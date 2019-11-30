package com.example.hornpub_travel_app.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.hornpub_travel_app.R;
import com.example.hornpub_travel_app.model.create_tour.StopPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class StopPointDialogFragment extends DialogFragment {
   private static final String TAG = "StopPointDialogFragment";
   private EditText editTextStopPointName, editTextAddress, editTextMinCost, editTextMaxCost;
   private Spinner spinnerServiceType, spinnerProvince;
   private TextView textViewTimeArrive, textViewTimeLeave, textViewDateArrive, textViewDateLeave;
   private ImageButton imageButtonClose;
   private StopPoint stopPoint;
   private String province;
   private Button buttonList, buttonAdd;

   public StopPointDialogFragment() {

   }
   public static StopPointDialogFragment newInstance(StopPoint stopPoint, String province) {
      StopPointDialogFragment fragment = new StopPointDialogFragment();
      fragment.setData(stopPoint, province);
      return fragment;
   }

   public void setData(StopPoint stopPoint, String province) {
      this.stopPoint = stopPoint;
      this.province = province;
   }
   @Override
   public void onStart() {
      super.onStart();
      getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
   }
   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.stop_point_infomation_dialog, container, false);
   }
   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      mapping();
      editTextAddress.setText(this.stopPoint.getAddress());
      try {
         createSpinnerServiceType();
         createSpinnerProvince();
      } catch (JSONException e) {
         e.printStackTrace();
      }
      getDialog().setCancelable(false);
      imageButtonClose.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Log.d(TAG, "onCLick");
            getDialog().dismiss();
         }
      });
      textViewDateArrive.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            datePicker(textViewDateArrive);
         }
      });
      textViewDateLeave.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            datePicker(textViewDateLeave);
         }
      });
      textViewTimeArrive.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            timePicker(textViewTimeArrive);
         }
      });
      textViewTimeLeave.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            timePicker(textViewTimeLeave);
         }
      });
      buttonAdd.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            if (getData()) getDialog().dismiss();
         }
      });
   }
   private void mapping() {
      editTextStopPointName = getView().findViewById(R.id.editTextStopPointName);
      editTextAddress = getView().findViewById(R.id.editTextAddress);
      editTextMinCost = getView().findViewById(R.id.editTextMinCost);
      editTextMaxCost = getView().findViewById(R.id.editTextMaxCost);
      spinnerServiceType = getView().findViewById(R.id.spinnerServiceType);
      spinnerProvince = getView().findViewById(R.id.spinnerProvince);
      textViewTimeArrive = getView().findViewById(R.id.textViewSelectTimeArrive);
      textViewTimeLeave = getView().findViewById(R.id.textViewSelectTimeLeave);
      textViewDateArrive = getView().findViewById(R.id.textViewSelectDateArrive);
      textViewDateLeave = getView().findViewById(R.id.textViewSelectDateLeave);
      imageButtonClose = getView().findViewById(R.id.buttonCloseDialog);
      buttonList = getView().findViewById(R.id.buttonListPointStop);
      buttonAdd = getView().findViewById(R.id.buttonSend);
   }
   @Override
   public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
   }

   private void createSpinnerServiceType() throws JSONException {
      List<String> stringList;
      ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, readeJSON("service_type"));
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      spinnerServiceType.setAdapter(adapter);
   }

   private void createSpinnerProvince() throws JSONException {
      List<String> stringList = readeJSON("province");
      ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stringList);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      spinnerProvince.setAdapter(adapter);
      for (int i = 0; i < stringList.size(); i++)
         if (stringList.get(i).equals(province))
            spinnerProvince.setSelection(i);
   }

   private List<String> readeJSON(String name) throws JSONException {
      List<String> stringArrayList = new ArrayList<>();
      String jsonString = "";
      try {
         InputStream is = getResources().openRawResource(getResources().getIdentifier(name, "raw", getActivity().getPackageName()));
         int size = is.available();
         byte[] buffer = new byte[size];
         is.read(buffer);
         is.close();
         jsonString = new String(buffer, "UTF-8");
      } catch (IOException ex) {
         ex.printStackTrace();
      }
      JSONArray jsonArray = new JSONArray(jsonString);
      for (int i = 0; i < jsonArray.length(); i++) {
         JSONObject json_data = jsonArray.getJSONObject(i);
         stringArrayList.add((json_data.getString("title")));
      }
      return stringArrayList;
   }

   private void datePicker(final TextView textView) {
      final Calendar myCalendar = Calendar.getInstance();
      int day = myCalendar.get(Calendar.DAY_OF_MONTH);
      int month = myCalendar.get(Calendar.MONTH);
      int year = myCalendar.get(Calendar.YEAR);
      DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
         @Override
         public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            myCalendar.set(Calendar.YEAR, i);
            myCalendar.set(Calendar.MONTH, i1);
            myCalendar.set(Calendar.DAY_OF_MONTH, i2);
            String myFormat = "dd-MM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            textView.setText(sdf.format(myCalendar.getTime()));
            textView.invalidate();
            textView.requestLayout();
         }
      }, year, month, day);
      datePickerDialog.show();
   }
   private void timePicker(final TextView textView) {
      final Calendar calendar = Calendar.getInstance();
      int hour = calendar.get(Calendar.HOUR);
      int min = calendar.get(Calendar.MINUTE);
      TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
         @Override
         public void onTimeSet(TimePicker timePicker, int i, int i1) {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            calendar.set(0, 0, 0, i, i1);
            if (textView == textViewTimeArrive) {
               stopPoint.setTimeArrive(simpleDateFormat.format(calendar.getTime()));
               textView.setText(stopPoint.getTimeArrive());
            } else {
               stopPoint.setTimeLeave(simpleDateFormat.format(calendar.getTime()));
               textView.setText(stopPoint.getTimeLeave());
            }
         }
      }, hour, min, false);
      timePickerDialog.show();
   }
   private boolean getData() {
      if (editTextStopPointName.getText().toString().equals("")) {
         Toast.makeText(getActivity(), "Tên điểm dừng không được rỗng", Toast.LENGTH_SHORT).show();
         return false;
      }
      if (editTextAddress.getText().toString().equals("")) {
         Toast.makeText(getActivity(), "Địa chỉ không được rỗng", Toast.LENGTH_SHORT).show();
         return false;
      }
      stopPoint.setName(editTextStopPointName.getText().toString());
      stopPoint.setServiceTypeId(spinnerServiceType.getSelectedItemPosition() + 1);
      stopPoint.setProvinceId(spinnerProvince.getSelectedItemPosition() + 1);
      stopPoint.setAddress(editTextAddress.getText().toString());
      if (!editTextMinCost.getText().toString().equals(""))
         stopPoint.setMinCost(Integer.valueOf(editTextMinCost.getText().toString()));
      if (!editTextMaxCost.getText().toString().equals(""))
         stopPoint.setMaxCost(Integer.valueOf(editTextMaxCost.getText().toString()));
      stopPoint.setArrivalAt(convertDateTimeToLong(textViewTimeArrive.getText().toString(), textViewDateArrive.getText().toString()));
      stopPoint.setLeaveAt(convertDateTimeToLong(textViewTimeLeave.getText().toString(), textViewDateLeave.getText().toString()));
      return true;
   }
   private long convertDateTimeToLong(String time, String date) {
      return 0;
   }

   @Override
   public void onDismiss(@NonNull DialogInterface dialog) {
      super.onDismiss(dialog);
      final Activity activity = getActivity();
      if (activity instanceof DialogInterface.OnDismissListener) {
         ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
      }
   }

}
