package fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.phatipan.guessthenumber.R;

import java.util.Random;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class SecondFragment extends Fragment {
    Random rand = new Random();
    private int level;
    private TextView tvNumber;
    private int number;
    private Button btnOk;
    private EditText edText;
    private TextView tvHint;
    private int timesLv1 = 4;
    private int timesLv2 = 9;
    private int input;
    private TextView tvTimes;
    private TextView tvEnter;

    public SecondFragment() {
        super();
    }

    public static SecondFragment newInstance(int level) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putInt("level", level);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Read from Arguments
        level = getArguments().getInt("level");

        if (level == 1) number = rand.nextInt(10);
        else if (level == 2) number = rand.nextInt(8999) + 1000;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_second, container, false);
        initInstances(rootView);
        return rootView;


    }

    private void initInstances(View rootView) {
        // init instance with rootView.findViewById here
        tvNumber = (TextView) rootView.findViewById(R.id.number);
        edText = (EditText) rootView.findViewById(R.id.input);
        tvHint = (TextView) rootView.findViewById(R.id.hint);
        tvTimes = (TextView) rootView.findViewById(R.id.times);
        tvEnter = (TextView) rootView.findViewById(R.id.enter);

        setTvNumber();



        if (level == 1) {
            tvTimes.setText("You have " + timesLv1 + " times");
            tvEnter.setText("Enter 0-9");
            edText.setHint("Enter 0-9");
            MaxLength(1);



            edText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i == EditorInfo.IME_ACTION_SEND) {

                        try {
                            input = Integer.parseInt(edText.getText().toString());
                        } catch (NumberFormatException e) {
                            input = 0; // your default value
                        }

                        if (number < input) tvHint.setText(input + " is Higher");
                        else if (number > input) tvHint.setText(input + " is Lower");
                        else {
                            timesLv1++;
                            tvHint.setText("True");
                            answer();

                            AlertDialog("You Win");
                        }

                        timesLv1--;
                        if (timesLv1 == 0) {
                            AlertDialog("Lose");
                            answer();
                        }
                        tvTimes.setText("You have " + timesLv1 + " times");
                        return true;
                    }
                    return false;
                }
            });
        }
        if (level == 2) {
            tvTimes.setText("You have " + timesLv2 + " times");
            tvEnter.setText("Enter 1000-9999");
            edText.setHint("Enter 1000-9999");
            MaxLength(4);

            edText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i == EditorInfo.IME_ACTION_SEND ) {

                        try {
                            input = Integer.parseInt(edText.getText().toString());
                        } catch (NumberFormatException e) {
                            input = 0; // your default value
                        }


                        if (number < input) tvHint.setText(input + " is Higher");
                        else if (number > input) tvHint.setText(input + " is Lower ");
                        else {
                            timesLv2++;
                            tvHint.setText("True");
                            tvNumber.setText("" + number);
                            AlertDialog("You Win");
                        }

                        timesLv2--;
                        if (timesLv2 == 6) {
                            answer();
                        } else if (timesLv2 == 3) {
                            answer();
                        } else if (timesLv2 == 0) {
                            AlertDialog("Lose");
                            answer();
                        }
                        tvTimes.setText("You have " + timesLv2 + " times");
                        return true;
                    }
                    return false;
                }
            });
        }


    }

    private void MaxLength(int i) {
        int maxLength = i;
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        edText.setFilters(fArray);
    }

    private void AlertDialog(String tv) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle(tv);
        dialog.setCancelable(false);
        dialog.setMessage("Play Again");

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentContainer, SecondFragment.newInstance(level))
                        .addToBackStack(null)
                        .commit();
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                getFragmentManager().popBackStack();
            }
        });

        dialog.show();
    }

    private void setTvNumber() {
        if (level == 1) tvNumber.setText("_");
        else if (level == 2) tvNumber.setText("_ _ _ _");
    }

    private void answer() {
        if (level == 1) tvNumber.setText("" + number);
        else if (level == 2) {
            if (timesLv2 == 6) {
                tvNumber.setText(number / 100 + " _ _");
            } else if (timesLv2 == 3) {
                tvNumber.setText(number / 10 + " _");
            } else if (timesLv2 == 0) {
                tvNumber.setText("" + number);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }

}
