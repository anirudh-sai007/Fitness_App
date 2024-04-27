package com.example.tbar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Bluetooth extends Fragment {

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;
    private final Set<String> scannedDevicesSet = new HashSet<>();
    private final ArrayList<String> deviceList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothGattCharacteristic mCharacteristic;

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;
    private static final String DEVICE_MAC_ADDRESS = "F4:12:FA:67:38:39"; // Predefined MAC address for automatic connection

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final BluetoothManager bluetoothManager = (BluetoothManager) requireContext().getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bluetooth, container, false);
        ListView listView = view.findViewById(R.id.deviceListView);
        adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, deviceList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPermissionsAndStartScanning();
    }

    private void checkPermissionsAndStartScanning() {
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            requestBluetoothEnable();
        } else if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
        } else {
            startScanning();
        }
    }

    private void requestBluetoothEnable() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
    }

    private void startScanning() {
        if (!isPermissionsGranted()) {
            requestBluetoothPermissions();
            return;
        }

        ScanSettings settings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .build();

        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        if (mBluetoothLeScanner != null) {
            mBluetoothLeScanner.startScan(null, settings, scanCallback);
        } else {
            Toast.makeText(getActivity(), "BLE scanner unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    private final ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            BluetoothDevice device = result.getDevice();
            String deviceAddress = device.getAddress();
            if (DEVICE_MAC_ADDRESS.equals(deviceAddress)) {
                connectToDevice(deviceAddress);
                stopScanning(); // Optionally stop scanning once the target device is found
            }
        }
    };

    private void stopScanning() {
        if (mBluetoothLeScanner != null && isPermissionsGranted()) {
            mBluetoothLeScanner.stopScan(scanCallback);
        }
    }

    private boolean isPermissionsGranted() {
        return ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestBluetoothPermissions() {
        ActivityCompat.requestPermissions(requireActivity(),
                new String[]{Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT},
                REQUEST_ENABLE_BT);
    }

    private void connectToDevice(String deviceAddress) {
        if (!isPermissionsGranted()) {
            Toast.makeText(getActivity(), "Permission not granted for Bluetooth connection", Toast.LENGTH_SHORT).show();
            return;
        }

        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(deviceAddress);
        mBluetoothGatt = device.connectGatt(getActivity(), false, gattCallback);
        Toast.makeText(getActivity(), "Connecting to " + deviceAddress, Toast.LENGTH_SHORT).show();
    }

    private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothGatt.STATE_CONNECTED) {
                gatt.discoverServices();
                requireActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Connected", Toast.LENGTH_SHORT).show());
            } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                requireActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Disconnected", Toast.LENGTH_SHORT).show());
            }
        }

        @SuppressLint("MissingPermission")
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                String SERVICE_UUID = "4fafc201-1fb5-459e-8fcc-c5c9c331914b";
                BluetoothGattService service = gatt.getService(UUID.fromString(SERVICE_UUID));
                if (service != null) {
                    String CHARACTERISTIC_UUID = "beb5483e-36e1-4688-b7f5-ea07361b26a8";
                    mCharacteristic = service.getCharacteristic(UUID.fromString(CHARACTERISTIC_UUID));
                    gatt.setCharacteristicNotification(mCharacteristic, true);
                    BluetoothGattDescriptor descriptor = mCharacteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    gatt.writeDescriptor(descriptor);
                }
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        stopScanning();
        closeGattConnection();
    }

    private void closeGattConnection() {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.close();
            mBluetoothGatt = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_OK) {
            startScanning();
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(getActivity(), "Bluetooth is required for this app to function", Toast.LENGTH_SHORT).show();
        }
    }
}
