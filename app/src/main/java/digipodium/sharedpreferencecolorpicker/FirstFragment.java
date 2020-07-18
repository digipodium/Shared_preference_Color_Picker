package digipodium.sharedpreferencecolorpicker;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class FirstFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private SeekBar sbRed;
    private SeekBar sbGreen;
    private SeekBar sbBlue;
    private Button btnSave;
    private ConstraintLayout wrapper;
    int colorRed = 100;
    int colorGreen = 100;
    int colorBlue = 100;
    private SharedPreferences appPref;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sbRed = view.findViewById(R.id.sbRed);
        sbGreen = view.findViewById(R.id.sbGreen);
        sbBlue = view.findViewById(R.id.sbBlue);
        wrapper = view.findViewById(R.id.wrapper);
        btnSave = view.findViewById(R.id.btnSave);

        appPref = getContext().getSharedPreferences("app_pref", Context.MODE_PRIVATE);

        // save the seekbar position
        // load the seekbar position

        sbRed.setOnSeekBarChangeListener(this);
        sbGreen.setOnSeekBarChangeListener(this);
        sbBlue.setOnSeekBarChangeListener(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = appPref.edit();
                editor.putInt("red", colorRed);
                editor.putInt("green", colorGreen);
                editor.putInt("blue", colorBlue);
                editor.apply();
                Snackbar.make(btnSave, "saved settings in memory", BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });
        Button btnClear = view.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appPref.edit().clear().apply();
                Snackbar.make(btnSave, "Cleared settings", BaseTransientBottomBar.LENGTH_LONG).show();
                updateUI(100, 100,100);
                sbRed.setProgress(100);
                sbGreen.setProgress(100);
                sbBlue.setProgress(100);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        int red = appPref.getInt("red", 100);
        int green= appPref.getInt("green", 250);
        int blue= appPref.getInt("blue", 0);

        updateUI(red,green,blue);
        sbRed.setProgress(red);
        sbGreen.setProgress(green);
        sbBlue.setProgress(blue);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
            case R.id.sbRed:
                colorRed = i;
                break;
            case R.id.sbGreen:
                colorGreen = i;
                break;
            case R.id.sbBlue:
                colorBlue = i;
                break;
        }
        updateUI(colorRed, colorGreen, colorBlue);
    }

    private void updateUI(int r, int g, int b) {
        wrapper.setBackgroundColor(Color.rgb(r, g, b));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // not to be used
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // not to be used
    }
}