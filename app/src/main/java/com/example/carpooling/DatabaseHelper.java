package com.example.carpooling;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.carpooling.Models.Trip;
import com.example.carpooling.Models.User;
import com.example.carpooling.Models.Vehicle;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name and Version
    private static final String DATABASE_NAME = "CarPooling.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USER = "User";
    private static final String TABLE_VEHICLE = "Vehicle";
    private static final String TABLE_TRIP = "Trip";
    private static final String TABLE_ATTENDEE = "Attendee";

    // User table columns
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_RATING = "rating";
    private static final String COLUMN_USER_IS_DRIVER = "isDriver";
    private static final String COLUMN_USER_VEHICLE_ID = "vehicleId";

    // Vehicle table columns
    private static final String COLUMN_VEHICLE_ID = "id";
    private static final String COLUMN_VEHICLE_MODEL = "model";
    private static final String COLUMN_VEHICLE_BRAND = "brand";

    // Trip table columns
    private static final String COLUMN_TRIP_ID = "id";
    private static final String COLUMN_TRIP_DRIVER_ID = "driverId"; // Foreign key to User table
    private static final String COLUMN_TRIP_DESTINATION = "destination";
    private static final String COLUMN_TRIP_LAT_START = "latStart";
    private static final String COLUMN_TRIP_LON_START = "lonStart";
    private static final String COLUMN_TRIP_LAT_FINISH = "latFinish";
    private static final String COLUMN_TRIP_LON_FINISH = "lonFinish";
    private static final String COLUMN_TRIP_PRICE = "price";
    private static final String COLUMN_TRIP_START = "start";

    // Attendee table columns
    private static final String COLUMN_ATTENDEE_USER_ID = "userId"; // Foreign key to User table
    private static final String COLUMN_ATTENDEE_TRIP_ID = "tripId"; // Foreign key to Trip table

    // SQL Query to Create Tables
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create User table
        db.execSQL("CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT, " +
                COLUMN_USER_EMAIL + " TEXT, " +
                COLUMN_USER_PASSWORD + " TEXT, " +
                COLUMN_USER_RATING + " INTEGER, " +
                COLUMN_USER_IS_DRIVER + " INTEGER, " +
                COLUMN_USER_VEHICLE_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_USER_VEHICLE_ID + ") REFERENCES " + TABLE_VEHICLE + "(" + COLUMN_VEHICLE_ID + "))");

        // Create Vehicle table
        db.execSQL("CREATE TABLE " + TABLE_VEHICLE + " (" +
                COLUMN_VEHICLE_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_VEHICLE_MODEL + " TEXT, " +
                COLUMN_VEHICLE_BRAND + " TEXT)");

        // Create Trip table
        db.execSQL("CREATE TABLE " + TABLE_TRIP + " (" +
                COLUMN_TRIP_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_TRIP_DRIVER_ID + " INTEGER, " +
                COLUMN_TRIP_DESTINATION + " TEXT, " +
                COLUMN_TRIP_LAT_START + " REAL, " +
                COLUMN_TRIP_LON_START + " REAL, " +
                COLUMN_TRIP_LAT_FINISH + " REAL, " +
                COLUMN_TRIP_LON_FINISH + " REAL, " +
                COLUMN_TRIP_START + " TEXT, " +
                COLUMN_TRIP_PRICE + " REAL, " +
                "FOREIGN KEY(" + COLUMN_TRIP_DRIVER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "))");

        // Create Attendee table
        db.execSQL("CREATE TABLE " + TABLE_ATTENDEE + " (" +
                COLUMN_ATTENDEE_USER_ID + " INTEGER, " +
                COLUMN_ATTENDEE_TRIP_ID + " INTEGER, " +
                "PRIMARY KEY (" + COLUMN_ATTENDEE_USER_ID + ", " + COLUMN_ATTENDEE_TRIP_ID + "), " +
                "FOREIGN KEY(" + COLUMN_ATTENDEE_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                "FOREIGN KEY(" + COLUMN_ATTENDEE_TRIP_ID + ") REFERENCES " + TABLE_TRIP + "(" + COLUMN_TRIP_ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VEHICLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDEE);
        onCreate(db);
    }

    //Insert example data
    public void insertExampleData() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Insert into Vehicle
        ContentValues vehicleValues = new ContentValues();
        vehicleValues.put(COLUMN_VEHICLE_ID, 1);
        vehicleValues.put(COLUMN_VEHICLE_MODEL, "Civic");
        vehicleValues.put(COLUMN_VEHICLE_BRAND, "Honda");
        db.insert(TABLE_VEHICLE, null, vehicleValues);

        // Insert into User
        ContentValues userValues = new ContentValues();
        userValues.put(COLUMN_USER_ID, 1);
        userValues.put(COLUMN_USER_NAME, "JohnDoe");
        userValues.put(COLUMN_USER_EMAIL, "john@example.com");
        userValues.put(COLUMN_USER_PASSWORD, "johndoe123");
        userValues.put(COLUMN_USER_RATING, 5);
        userValues.put(COLUMN_USER_IS_DRIVER, 1);
        userValues.put(COLUMN_USER_VEHICLE_ID, 1);
        db.insert(TABLE_USER, null, userValues);

        // Insert into Trip
        ContentValues tripValues = new ContentValues();
        tripValues.put(COLUMN_TRIP_ID, 1);
        tripValues.put(COLUMN_TRIP_DRIVER_ID, 1);
        tripValues.put(COLUMN_TRIP_DESTINATION, "City Center");
        tripValues.put(COLUMN_TRIP_LAT_START, 41.0457);
        tripValues.put(COLUMN_TRIP_LON_START, 21.290);
        tripValues.put(COLUMN_TRIP_LAT_FINISH, 50);
        tripValues.put(COLUMN_TRIP_LON_FINISH, 50);
        tripValues.put(COLUMN_TRIP_START, "2024-12-08T10:00:00");
        tripValues.put(COLUMN_TRIP_PRICE, 10.5);
        db.insert(TABLE_TRIP, null, tripValues);

        // Insert into Attendee
        ContentValues attendeeValues = new ContentValues();
        attendeeValues.put(COLUMN_ATTENDEE_USER_ID, 1);
        attendeeValues.put(COLUMN_ATTENDEE_TRIP_ID, 1);
        db.insert(TABLE_ATTENDEE, null, attendeeValues);

        db.close();
    }

    // CRUD User
    public long addUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);

        // Use default values for other fields
        int defaultRating = 0;  // Default rating (can be changed based on your needs)
        boolean defaultIsDriver = false;  // Default value for isDriver
        int defaultVehicleId = -1;  // Default value for vehicleId (or you can set it to NULL)

        values.put(COLUMN_USER_RATING, defaultRating);
        values.put(COLUMN_USER_IS_DRIVER, defaultIsDriver ? 1 : 0);
        values.put(COLUMN_USER_VEHICLE_ID, defaultVehicleId);  // This can be -1 or NULL (if your DB supports it)

        // Insert the user into the database
        long result = db.insert(TABLE_USER, null, values);
        db.close();
        return result;
    }


    public List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER, null);
        if (cursor.moveToFirst()) {
            do {
                users.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return users;
    }

    public int updateUser(int id, String username, String email, String password, int rating, boolean isDriver, Integer vehicleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, username);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);
        values.put(COLUMN_USER_RATING, rating);
        values.put(COLUMN_USER_IS_DRIVER, isDriver ? 1 : 0);
        values.put(COLUMN_USER_VEHICLE_ID, vehicleId);
        int rowsAffected = db.update(TABLE_USER, values, COLUMN_USER_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }

    public void deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, COLUMN_USER_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }


    // CRUD Vehicle
    public int addVehicle(String brand, String model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_VEHICLE_BRAND, brand);
        values.put(COLUMN_VEHICLE_MODEL, model);

        long result = db.insert(TABLE_VEHICLE, null, values);
        db.close();

        if (result == -1) {
            return -1; // Vehicle not inserted
        }

        // Retrieve the ID of the newly added vehicle
        SQLiteDatabase readableDb = this.getReadableDatabase();
        Cursor cursor = readableDb.rawQuery("SELECT id FROM " + TABLE_VEHICLE + " WHERE brand = ? AND model = ?",
                new String[]{brand, model});

        if (cursor != null && cursor.moveToFirst()) {
            int vehicleId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            cursor.close();
            return vehicleId;
        }

        return -1; // In case we fail to fetch the vehicle ID
    }


    public List<String> getAllVehicles() {
        List<String> vehicles = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_VEHICLE, null);
        if (cursor.moveToFirst()) {
            do {
                vehicles.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_MODEL)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return vehicles;
    }

    public int updateVehicle(int id, String model, String brand) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_VEHICLE_MODEL, model);
        values.put(COLUMN_VEHICLE_BRAND, brand);
        int rowsAffected = db.update(TABLE_VEHICLE, values, COLUMN_VEHICLE_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }

    public void deleteVehicle(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VEHICLE, COLUMN_VEHICLE_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // CRUD Trip
    public long addTrip(int driverId, String destination, double latStart, double lonStart,
                        double latFinish, double lonFinish, double price, String start) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRIP_DRIVER_ID, driverId);
        values.put(COLUMN_TRIP_DESTINATION, destination);
        values.put(COLUMN_TRIP_LAT_START, latStart);
        values.put(COLUMN_TRIP_LON_START, lonStart);
        values.put(COLUMN_TRIP_LAT_FINISH, latFinish);
        values.put(COLUMN_TRIP_LON_FINISH, lonFinish);
        values.put(COLUMN_TRIP_PRICE, price);
        values.put(COLUMN_TRIP_START, start);

        long result = db.insert(TABLE_TRIP, null, values);
        db.close();
        return result;
    }

    @SuppressLint("NewApi")
    public ArrayList<Trip> getAllTripsWithDriverAndVehicle() {
        ArrayList<Trip> trips = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " +
                "t." + COLUMN_TRIP_ID + " AS trip_id, " +
                "t." + COLUMN_TRIP_DRIVER_ID + ", " +
                "t." + COLUMN_TRIP_DESTINATION + ", " +
                "t." + COLUMN_TRIP_LAT_START + ", " +
                "t." + COLUMN_TRIP_LON_START + ", " +
                "t." + COLUMN_TRIP_LAT_FINISH + ", " +
                "t." + COLUMN_TRIP_LON_FINISH + ", " +
                "t." + COLUMN_TRIP_START + ", " +
                "t." + COLUMN_TRIP_PRICE + ", " +
                "u." + COLUMN_USER_ID + " AS user_id, " +
                "u." + COLUMN_USER_NAME + ", " +
                "u." + COLUMN_USER_EMAIL + ", " +
                "u." + COLUMN_USER_PASSWORD + ", " +
                "u." + COLUMN_USER_RATING + ", " +
                "u." + COLUMN_USER_IS_DRIVER + ", " +
                "u." + COLUMN_USER_VEHICLE_ID + ", " +
                "v." + COLUMN_VEHICLE_ID + " AS vehicle_id, " +
                "v." + COLUMN_VEHICLE_MODEL + ", " +
                "v." + COLUMN_VEHICLE_BRAND +
                " FROM " + TABLE_TRIP + " t " +
                "JOIN " + TABLE_USER + " u ON t." + COLUMN_TRIP_DRIVER_ID + " = u." + COLUMN_USER_ID + " " +
                "JOIN " + TABLE_VEHICLE + " v ON u." + COLUMN_USER_VEHICLE_ID + " = v." + COLUMN_VEHICLE_ID;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // Extract Trip details
                Integer tripId = cursor.getInt(cursor.getColumnIndexOrThrow("trip_id"));
                String destination = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_DESTINATION));
                Double latStart = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_LAT_START));
                Double lonStart = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_LON_START));
                Double latFinish = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_LAT_FINISH));
                Double lonFinish = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_LON_FINISH));
                String start = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_START));
                Double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_PRICE));

                // Create Trip object and use setters
                Trip trip = new Trip();
                trip.setId(tripId);
                trip.setDestination(destination);
                trip.setLatStart(latStart);
                trip.setLonStart(lonStart);
                trip.setLatFinish(latFinish);
                trip.setLonFinish(lonFinish);
                trip.setStart(LocalDateTime.parse(start)); // Assuming the start date is in ISO format
                trip.setPrice(price);

                // Create User (Driver) object and use setters
                User driver = new User();
                driver.setId(cursor.getInt(cursor.getColumnIndexOrThrow("user_id")));
                driver.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME)));
                driver.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL)));
                driver.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PASSWORD)));
                driver.setRating(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_RATING)));
                driver.setDriver(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_IS_DRIVER)) == 1);
                driver.setVehicleId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_VEHICLE_ID)));

                // Create Vehicle object for Driver and use setters
                Vehicle vehicle = new Vehicle();
                vehicle.setId(cursor.getInt(cursor.getColumnIndexOrThrow("vehicle_id")));
                vehicle.setModel(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_MODEL)));
                vehicle.setBrand(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_BRAND)));

                // Set Driver and Vehicle to the Trip
                driver.setVehicle(vehicle);
                trip.setDriver(driver);

                trips.add(trip);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return trips;
    }

    public int updateTrip(int id, int driverId, String destination, double latStart, double lonStart, double latFinish, double lonFinish, String start, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRIP_DRIVER_ID, driverId);
        values.put(COLUMN_TRIP_DESTINATION, destination);
        values.put(COLUMN_TRIP_LAT_START, latStart);
        values.put(COLUMN_TRIP_LON_START, lonStart);
        values.put(COLUMN_TRIP_LAT_FINISH, latFinish);
        values.put(COLUMN_TRIP_LON_FINISH, lonFinish);
        values.put(COLUMN_TRIP_START, start);
        values.put(COLUMN_TRIP_PRICE, price);
        int rowsAffected = db.update(TABLE_TRIP, values, COLUMN_TRIP_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }

    public void deleteTrip(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRIP, COLUMN_TRIP_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // CRUD Attendee
    public long addAttendee(int userId, int tripId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ATTENDEE_USER_ID, userId);
        values.put(COLUMN_ATTENDEE_TRIP_ID, tripId);

        long result = db.insert(TABLE_ATTENDEE, null, values);
        db.close();
        return result;
    }

    public List<String> getAllAttendees() {
        List<String> attendees = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ATTENDEE, null);
        if (cursor.moveToFirst()) {
            do {
                attendees.add("User ID: " + cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ATTENDEE_USER_ID)) +
                        ", Trip ID: " + cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ATTENDEE_TRIP_ID)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return attendees;
    }

    public void deleteAttendee(int userId, int tripId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ATTENDEE, COLUMN_ATTENDEE_USER_ID + "=? AND " + COLUMN_ATTENDEE_TRIP_ID + "=?",
                new String[]{String.valueOf(userId), String.valueOf(tripId)});
        db.close();
    }

    //Method to Validate Login

    public boolean validateLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,
                new String[]{COLUMN_USER_ID},
                COLUMN_USER_EMAIL + "=? AND " + COLUMN_USER_PASSWORD + "=?",
                new String[]{email, password},
                null, null, null);

        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }
    public boolean registerUser(String email, String password, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the user already exists
        Cursor cursor = db.query(TABLE_USER,
                new String[]{COLUMN_USER_ID},
                COLUMN_USER_EMAIL + "=?",
                new String[]{email},
                null, null, null);

        if (cursor.getCount() > 0) {
            // User already exists
            cursor.close();
            return false;
        }
        cursor.close();

        // User doesn't exist, proceed to insert new user
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);
        values.put(COLUMN_USER_NAME, name);

        long result = db.insert(TABLE_USER, null, values);
        return result != -1;
    }

//Get user
public User getUser(String email, String password) {
    SQLiteDatabase db = this.getReadableDatabase();
    User user = null;

    Cursor cursor = db.rawQuery(
            "SELECT * FROM User WHERE email = ? AND password = ?",
            new String[]{email, password}
    );

    if (cursor.moveToFirst()) {
        // Extract data from the cursor
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String userEmail = cursor.getString(cursor.getColumnIndexOrThrow("email"));
        String userPassword = cursor.getString(cursor.getColumnIndexOrThrow("password"));
        int rating = cursor.getInt(cursor.getColumnIndexOrThrow("rating"));
        int vehicleId = cursor.isNull(cursor.getColumnIndexOrThrow("vehicleId")) ? null : cursor.getInt(cursor.getColumnIndexOrThrow("vehicleId"));
        boolean isDriver = cursor.getInt(cursor.getColumnIndexOrThrow("isDriver")) == 1;

        // Create the User object
        user = new User(id, name, userEmail, userPassword, rating, isDriver, vehicleId);
    }

    cursor.close();
    return user;
}

    //Get User ID
public int getUserId(String email, String password) {
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(
            "SELECT id FROM User WHERE email = ? AND password = ?",
            new String[]{email, password}
    );

    if (cursor.moveToFirst()) {
        int userId = cursor.getInt(0);
        cursor.close();
        return userId;
    }
    cursor.close();
    return -1;
}

//Get vehicle by its id
public Vehicle getVehicleById(Integer id) {
    Vehicle vehicle = null;
    SQLiteDatabase db = this.getReadableDatabase();
    String query = "SELECT * FROM " + TABLE_VEHICLE + " WHERE " + COLUMN_VEHICLE_ID + " = ?";
    Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
    if (cursor.moveToFirst()) {
        vehicle = new Vehicle(
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_MODEL)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_BRAND))
        );
    }
    cursor.close();
    db.close();
    return vehicle;
}

//get trip ids
public List<Integer> getTripIdsByUserId(int userId) {
    SQLiteDatabase db = this.getReadableDatabase();
    List<Integer> tripIds = new ArrayList<>();  // List to store tripIds

    // Query to get all tripIds associated with the given userId
    String query = "SELECT " + COLUMN_ATTENDEE_TRIP_ID + " FROM " + TABLE_ATTENDEE
            + " WHERE " + COLUMN_ATTENDEE_USER_ID + " = ?";
    Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

    // Check if we have any results
    if (cursor != null && cursor.moveToFirst()) {
        // Loop through the results and add each tripId to the list
        do {
            int tripId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ATTENDEE_TRIP_ID));
            tripIds.add(tripId);
        } while (cursor.moveToNext());
        cursor.close();
    }

    return tripIds;
}

    public User getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        // Query to get the user by ID
        Cursor cursor = db.rawQuery(
                "SELECT * FROM User WHERE id = ?",
                new String[]{String.valueOf(userId)}
        );

        if (cursor.moveToFirst()) {
            // Extract data from the cursor
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            int rating = cursor.getInt(cursor.getColumnIndexOrThrow("rating"));
            int vehicleId = cursor.isNull(cursor.getColumnIndexOrThrow("vehicleId")) ? null : cursor.getInt(cursor.getColumnIndexOrThrow("vehicleId"));
            boolean isDriver = cursor.getInt(cursor.getColumnIndexOrThrow("isDriver")) == 1;

            // Create the User object
            user = new User(id, name, email, password, rating, isDriver, vehicleId);
        }

        cursor.close();
        return user;
    }
    public boolean isUserAttendee(int userId, int tripId) {
        SQLiteDatabase db = this.getReadableDatabase();

        // SQL query to check if there is an attendee record for the specific user and trip
        Cursor cursor = db.rawQuery("SELECT * FROM Attendee WHERE userId = ? AND tripId = ?",
                new String[]{String.valueOf(userId), String.valueOf(tripId)});

        boolean isAttendee = cursor.getCount() > 0; // If the query returns rows, user is an attendee
        cursor.close();

        return isAttendee;
    }




}

