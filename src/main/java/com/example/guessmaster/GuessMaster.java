//Amir Rafati
//Student Number: 20224896
//April 11 2022

package com.example.guessmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import java.util.Random;
import android.content.DialogInterface;

public class GuessMaster extends AppCompatActivity {
    private TextView entityName;
    private TextView ticketsum;
    private Button guessButton;
    private EditText userIn;
    private Button btnclearContent;
    private String user_input;
    private ImageView entityImage;
    String answer;
    private int numOfEntities;
    private Entity[] entities;
    private int[] tickets;
    String entName;
    int entity1 = 0;
    private Entity entityid1;
    int currentTicketWon = 0;

    Politician Trudeau = new Politician("Justin Trudeau", new Date("December", 25, 1971), "Male", "Liberal", 0.25);
    Singer Dion = new Singer("Celine Dion", new Date("March", 30, 1961), "Female", "La voix du bon Dieu", new Date("November", 6, 1981), 0.5);
    Person myCreator = new Person("myCreator", new Date("September", 1, 2000), "Female", 1);
    Country usa = new Country("United States", new Date("July", 4, 1776), "Washington D.C.", 0.1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the xml as the acitivity UI view
        setContentView(R.layout.activity_main);
        //Specify the button in the view
        guessButton = (Button) findViewById(R.id.btnGuess);
        //(Please note that the R.id references the name of the Guess Button defined in the xml file )
        //EditText for user input
        userIn = (EditText) findViewById(R.id.guessinput);
        //TextView for total tickets
        ticketsum = (TextView) findViewById(R.id.ticket);
        btnclearContent = (Button) findViewById(R.id.btnClear);
        entityName = (TextView) findViewById(R.id.entityname);
        entityImage = (ImageView) findViewById(R.id.entityimage);

        new GuessMaster();

        addEntity(Trudeau);
        addEntity(Dion);
        addEntity(myCreator);
        addEntity(usa);

        changeEntity();
        welcomeToGame(entityid1);

        btnclearContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEntity();
            }
        });

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame();
            }
        });



    }


    public GuessMaster() {
        numOfEntities = 0;
        entities = new Entity[10];
    }

    public void addEntity(Entity entity) {
//		entities[numOfEntities++] = new Entity(entity);
//		entities[numOfEntities++] = entity;//////
        entities[numOfEntities++] = entity.clone();
    }
    public void changeEntity() {
        int entityid = genRandomEntityId();
        Entity entity = entities[entityid];
        entityName.setText(entity.getName());
        ImageSetter(entity);
        entityid1 = entity;
    }
    public void ImageSetter(Entity entity) {
        if (entity.toString().equals(Trudeau.toString())){
            entityImage.setImageResource(R.drawable.justint);
        } else if (entity.toString().equals(usa.toString())){
            entityImage.setImageResource(R.drawable.usaflag);
        } else if (entity.toString().equals(myCreator.toString())){
            entityImage.setImageResource(R.drawable.mycreator);
        } else if (entity.toString().equals(Dion.toString())){
            entityImage.setImageResource(R.drawable.celidion);
        }
    }
    public void ContinueGame() {
        userIn.getText().clear();
        changeEntity();
    }

    public void welcomeToGame(Entity entity) {
        AlertDialog.Builder welcomealert = new AlertDialog.Builder(GuessMaster.this);
        welcomealert.setTitle("GuessMaster Game v3");
        welcomealert.setMessage(entity.welcomeMessage());
        welcomealert.setCancelable(false);
        welcomealert.setNegativeButton("START GAME", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Game is Starting...", Toast.LENGTH_SHORT).show();
            }
        });
        welcomealert.show();
    }

    public void playGame(int entityId) {
        Entity entity = entities[entityId];
        playGame(entity);
    }

    public void playGame(Entity entity) {
        answer = userIn.getText().toString();
        answer = answer.replace("\n", "").replace("\r", "");
        Date date = new Date(answer);

        if (date.precedes(entity.getBorn())) {
            AlertDialog.Builder lateralert = new AlertDialog.Builder(GuessMaster.this);
            lateralert.setIcon(R.drawable.ic_error_outline_black_24dp);
            lateralert.setTitle("Incorrect");
            lateralert.setMessage("Try a later date than " +date.toString());
            lateralert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(getBaseContext(), "Game is Starting...", Toast.LENGTH_SHORT).show();
                }
            });
            lateralert.show();

        } else if (entity.getBorn().precedes(date)) {
            AlertDialog.Builder earlyalert = new AlertDialog.Builder(GuessMaster.this);
            earlyalert.setIcon(R.drawable.ic_error_outline_black_24dp);
            earlyalert.setTitle("Incorrect");
            earlyalert.setMessage("Try an earlier date than " +date.toString());
            earlyalert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(getBaseContext(), "Game is Starting...", Toast.LENGTH_SHORT).show();
                }
            });
            earlyalert.show();

        } else {
            int ticketsWon = (int) ((entity.getAwardedTicketNumber()) * 100);
            currentTicketWon += ticketsWon;
            AlertDialog.Builder correctalert = new AlertDialog.Builder(GuessMaster.this);
            correctalert.setIcon(R.drawable.ic_check_circle_black_24dp);
            correctalert.setTitle("You Won!");
            correctalert.setMessage("Bingo! Congrats. The correct information is: \n" +entity.toString());
            correctalert.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ContinueGame();
                }
            });
            correctalert.show();
            ticketsum.setText("Total Tickets: " + currentTicketWon);
        }

    }


    public void playGame() {
        while (true) {
            int entityId = genRandomEntityId();
            playGame(entityId);
        }
    }

    public int genRandomEntityId() {
        Random randomNumber = new Random();
        return randomNumber.nextInt(numOfEntities);
    }










}