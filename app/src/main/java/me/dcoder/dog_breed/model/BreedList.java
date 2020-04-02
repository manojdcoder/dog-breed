package me.dcoder.dog_breed.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class BreedList implements Serializable {
   @SerializedName("message")
   public HashMap<String, ArrayList<String>> data;
}
