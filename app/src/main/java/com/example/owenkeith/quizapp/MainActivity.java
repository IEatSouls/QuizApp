package com.example.owenkeith.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int next = 0;
    private int score = 0;
    private boolean hasCheated = false;
    private Button trueButton, falseButton, nextButton, cheatButton;
    private TextView questionTextView;
    private TextView scoreTextView;
    private Question[] questionBank = {
            new Question(true, R.string.question_1),
            new Question(false, R.string.question_2),
            new Question(false, R.string.question_3),
            new Question(true, R.string.question_4)
    };
    private int currentIndex;
    private boolean cheatingEnabled;

    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_CURRENT_QUESTION = "current question";
    public static final String EXTRA_CURRENT_ANSWER = "current answer";
    public static final int REQUEST_CHEATED = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1. Wire the buttons & the textview
        trueButton = (Button) findViewById(R.id.button_true);
        falseButton = (Button) findViewById(R.id.button_false);
        nextButton = (Button) findViewById(R.id.button_next);
        cheatButton = (Button) findViewById(R.id.button_cheat);
        questionTextView = (TextView) findViewById(R.id.textview_question);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        //2. Create a new Question object from
        //   the String resources
        //   Make a Question object & pass the string resource
        //   & answer in the constructor
        //Question q1 = new Question(R.string.question_cats,true);

        //3. Set the textView's text to the Question's text
        currentIndex = 0;
        questionTextView.setText(questionBank[currentIndex].getQuestionID());
        scoreTextView.setText("Score: " + score);
        //4. Make a View.OnClickListener for each button
        //using the anonymous inner class way of doing things
        //Inside each button, call the question's checkAnswer method
        //and make an appropriate toast.
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
                disableButtons();
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
                disableButtons();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuestion();
                enableButtons();
            }
        });

        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //use Intents to go from one Activity to another
                Intent openCheatActivity =
                        new Intent(MainActivity.this,CheatActivity.class);
                //load up the intent with extra information to take
                //to the new activity
                //it follows the key:value pair format where
                //you have a key to identify the extra & a value that is
                //stored with it
                openCheatActivity.putExtra(EXTRA_CURRENT_QUESTION,
                        questionBank[currentIndex].getQuestionID());
                openCheatActivity.putExtra(EXTRA_CURRENT_ANSWER,
                        questionBank[currentIndex].isAnswerTrue());
                //create a constant for the start activity
                startActivityForResult(openCheatActivity, REQUEST_CHEATED);
            }
        });

        cheatingEnabled = false;
        cheatButton.setVisibility(View.GONE);
    }

    private void disableButtons() {
        trueButton.setEnabled(false);
        falseButton.setEnabled(false);
    }

    private void enableButtons(){
        trueButton.setEnabled(true);
        falseButton.setEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CHEATED){
            hasCheated = data.getBooleanExtra(CheatActivity.EXTRA_CHEATED, false);
        }
    }

    private void updateQuestion() {
        currentIndex =
                (currentIndex + 1) % questionBank.length;
        questionTextView.setText(questionBank[currentIndex].getQuestionID());
    }

    private void checkAnswer(boolean userResponse) {
        if(questionBank[currentIndex].checkAnswer(userResponse)) {
            Toast.makeText(MainActivity.this, R.string.toast_correct, Toast.LENGTH_SHORT).show();
            score++;
            scoreTextView.setText("Score: " + score);
            next++;
        } else {
            Toast.makeText(MainActivity.this, R.string.toast_incorrect, Toast.LENGTH_SHORT).show();
            if(score!=0){
                score--;
                scoreTextView.setText("Score: " + score);
            }
            next++;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_cheat:
                toggleCheating();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void toggleCheating() {
        if(cheatingEnabled) {
            cheatingEnabled = false;
            cheatButton.setVisibility(View.GONE);
        } else
        {
            cheatingEnabled = true;
            cheatButton.setVisibility(View.VISIBLE);
        }
    }

}