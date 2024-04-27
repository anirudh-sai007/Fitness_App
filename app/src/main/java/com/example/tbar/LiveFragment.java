package com.example.tbar;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LiveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LiveFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button a,b,c,d;
     BluetoothGatt mBluetoothGatt;

     BluetoothGattCharacteristic mCharacteristic;
     BluetoothAdapter mBluetoothAdapter;

     TextView rom;
    int rom_degrees = Integer.MIN_VALUE;
     ArrayList<String> messageList = new ArrayList<>();
     ArrayAdapter<String> messageAdapter;

    public LiveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LiveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LiveFragment newInstance(String param1, String param2) {
        LiveFragment fragment = new LiveFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_live, container, false);

        a=view.findViewById(R.id.buttona);
        b=view.findViewById(R.id.buttonb);
        c=view.findViewById(R.id.buttonc);
        d=view.findViewById(R.id.buttond);
        rom=view.findViewById(R.id.romvalue);
        ListView messageListView=view.findViewById(R.id.messageListView);
        messageAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, messageList);
        messageListView.setAdapter(messageAdapter);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c = "a";
                Toast.makeText(getActivity(), c, Toast.LENGTH_SHORT).show();

                sendcmdtoesp32(c);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c = "b";
                Toast.makeText(getActivity(), c, Toast.LENGTH_SHORT).show();

                sendcmdtoesp32(c);
            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String c = "c20";
                Toast.makeText(getActivity(), c, Toast.LENGTH_SHORT).show();

                sendcmdtoesp32(c);
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c = "d";
                Toast.makeText(getActivity(), c, Toast.LENGTH_SHORT).show();

                sendcmdtoesp32(c);
            }
        });
        return  view;
    }
    @SuppressLint("MissingPermission")
    private void readCharacteristic() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null || mCharacteristic == null) {
            return;
        }
        mBluetoothGatt.readCharacteristic(mCharacteristic);
    }
    private void sendcmdtoesp32(String command) {
        if (mBluetoothGatt == null || mCharacteristic == null) {

            Log.e("BLE", "BluetoothGatt or characteristic is null");
            return;
        }
        mCharacteristic.setValue(command.getBytes());
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        boolean status = mBluetoothGatt.writeCharacteristic(mCharacteristic);
        Log.d("BLE", "Write status: " + status);
    }

    private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @SuppressLint("MissingPermission")
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothGatt.STATE_CONNECTED) {
                getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Connected", Toast.LENGTH_SHORT).show());
                gatt.discoverServices();
            } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Disconnected", Toast.LENGTH_SHORT).show());
            }
        }
//        @SuppressLint("MissingPermission")
//        @Override
//        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
//            if (status == BluetoothGatt.GATT_SUCCESS) {
//                // Replace with your actual SERVICE_UUID
//                String SERVICE_UUID = "4fafc201-1fb5-459e-8fcc-c5c9c331914b";
//                BluetoothGattService service = gatt.getService(UUID.fromString(SERVICE_UUID));
//                if (service != null) {
//                    // Replace with your actual CHARACTERISTIC_UUID
//                    String CHARACTERISTIC_UUID = "beb5483e-36e1-4688-b7f5-ea07361b26a8";
//                    mCharacteristic = service.getCharacteristic(UUID.fromString(CHARACTERISTIC_UUID));
//                    gatt.setCharacteristicNotification(mCharacteristic, true);
//                    readCharacteristic();
//                }
//            }
//        }

        @SuppressLint("MissingPermission")
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                // Replace with your actual SERVICE_UUID
                String SERVICE_UUID = "4fafc201-1fb5-459e-8fcc-c5c9c331914b";
                BluetoothGattService service = gatt.getService(UUID.fromString(SERVICE_UUID));
                if (service != null) {
                    // Replace with your actual CHARACTERISTIC_UUID
                    String CHARACTERISTIC_UUID = "beb5483e-36e1-4688-b7f5-ea07361b26a8";
                    mCharacteristic = service.getCharacteristic(UUID.fromString(CHARACTERISTIC_UUID));

                    // Enable notifications for this characteristic
                    gatt.setCharacteristicNotification(mCharacteristic, true);

                    // Write on the config descriptor to enable notification
                    BluetoothGattDescriptor descriptor = mCharacteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    gatt.writeDescriptor(descriptor);

                    readCharacteristic();
                }
            }
        }


        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                displayData(characteristic.getValue());
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            displayData(characteristic.getValue());
        }
    };

    private void displayData(byte[] data) {
        if (data != null && data.length > 0) {
            getActivity().runOnUiThread(() -> {
                String dataStr = new String(data);
                messageList.add(dataStr); // Add the message to the list
                messageAdapter.notifyDataSetChanged();
                try {
                    int intData = Integer.parseInt(dataStr);

                    // Add valid data to the list
                    messageList.add(dataStr);
                    messageAdapter.notifyDataSetChanged();

//                    writeToFile(dataStr);

                    if (intData > rom_degrees) {
                        rom_degrees = intData;
                        rom.setText(String.valueOf(rom_degrees));
                    }
                } catch (NumberFormatException e) {
                    rom.setText(e.getMessage());
                }
            });
        }
    }
}