package com.example.locationfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "locations.db";
    private static final int DATABASE_VERSION = 1;

    // initialize column/table names
    public static final String TABLE_NAME = "locations";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ADDRESS + " TEXT, "
            + COLUMN_LATITUDE + " REAL, "
            + COLUMN_LONGITUDE + " REAL); ";

    private boolean initialLocationsInserted = false;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //function to add a new location to the database
    public boolean insertLocation(Location location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ADDRESS, location.getAddress());
        values.put(COLUMN_LATITUDE, location.getLatitude());
        values.put(COLUMN_LONGITUDE, location.getLongitude());
        long var = db.insert(TABLE_NAME, null, values);
        db.close();
        return var != -1;
    }

    // retrieve all locations from DB
    public Cursor retrieveAllLocations() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // retrieve location by address
    public Cursor retrieveLocationsByAddress(String address) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ADDRESS + " LIKE ?", new String[]{"%" + address + "%"});
    }

    // delete location by ID
    public void deleteLocationId(int id) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // update location
    public boolean updateData(Location location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ADDRESS, location.getAddress());
        values.put(COLUMN_LATITUDE, location.getLatitude());
        values.put(COLUMN_LONGITUDE, location.getLongitude());

        int result = db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(location.getId())});
        db.close();
        return result > 0;
    }

    // initialize database with 100 locations of Greater Toronto Area
    public void insertInitialLocations(Context context) {
        if (!initialLocationsInserted) {
            Location[] initialLocations = new Location[]{
                    new Location(0, "Toronto Maple Leafs Game at Scotiabank Arena", "43.6407", "-79.3791"),
                    new Location(0, "CN Tower", "43.6426", "-79.3871"),
                    new Location(0, "Royal Ontario Museum", "43.6677", "-79.3948"),
                    new Location(0, "Ripley's Aquarium of Canada", "43.6415", "-79.3853"),
                    new Location(0, "Toronto Islands", "43.6110", "-79.3064"),
                    new Location(0, "High Park", "43.6480", "-79.4633"),
                    new Location(0, "Toronto Zoo", "43.7711", "-79.1880"),
                    new Location(0, "Art Gallery of Ontario", "43.6536", "-79.3921"),
                    new Location(0, "St. Lawrence Market", "43.6487", "-79.3735"),
                    new Location(0, "Distillery District", "43.6500", "-79.3585"),
                    new Location(0, "Toronto City Hall", "43.6511", "-79.3839"),
                    new Location(0, "Eaton Centre", "43.6537", "-79.3802"),
                    new Location(0, "Toronto Harbourfront", "43.6402", "-79.3802"),
                    new Location(0, "Casa Loma", "43.7115", "-79.4111"),
                    new Location(0, "Toronto Botanical Garden", "43.7330", "-79.3585"),
                    new Location(0, "Royal Alexandra Theatre", "43.6476", "-79.3889"),
                    new Location(0, "Ontario Science Centre", "43.7259", "-79.3050"),
                    new Location(0, "Evergreen Brick Works", "43.6786", "-79.3630"),
                    new Location(0, "Kensington Market", "43.6543", "-79.4026"),
                    new Location(0, "Yonge-Dundas Square", "43.6543", "-79.3806"),
                    new Location(0, "Nathan Phillips Square", "43.6536", "-79.3832"),
                    new Location(0, "Bata Shoe Museum", "43.6704", "-79.3995"),
                    new Location(0, "Toronto Symphony Orchestra at Roy Thomson Hall", "43.6443", "-79.3873"),
                    new Location(0, "Harbourfront Centre", "43.6415", "-79.3797"),
                    new Location(0, "The Beaches", "43.6757", "-79.2914"),
                    new Location(0, "Woodbine Beach", "43.6694", "-79.2980"),
                    new Location(0, "Toronto Eaton Centre", "43.6537", "-79.3802"),
                    new Location(0, "Ontario Place", "43.6285", "-79.4901"),
                    new Location(0, "Scarborough Bluffs", "43.7075", "-79.2412"),
                    new Location(0, "Toronto Music Garden", "43.6287", "-79.3790"),
                    new Location(0, "Spadina Museum", "43.6810", "-79.4174"),
                    new Location(0, "Toronto Railway Museum", "43.6500", "-79.3720"),
                    new Location(0, "Ontario Heritage Centre", "43.6534", "-79.3803"),
                    new Location(0, "Toronto Islands Ferry", "43.6116", "-79.3265"),
                    new Location(0, "Harbourfront Canoe & Kayak Centre", "43.6408", "-79.3789"),
                    new Location(0, "Art Gallery of Ontario", "43.6536", "-79.3921"),
                    new Location(0, "Queen Street West", "43.6465", "-79.4107"),
                    new Location(0, "Toronto's Chinatown", "43.6530", "-79.3995"),
                    new Location(0, "Allan Gardens", "43.6594", "-79.3735"),
                    new Location(0, "Humber Bay Arch Bridge", "43.6211", "-79.4830"),
                    new Location(0, "Black Creek Pioneer Village", "43.7769", "-79.5016"),
                    new Location(0, "Royal Ontario Museum Gardens", "43.6677", "-79.3948"),
                    new Location(0, "Toronto Raptors Game at Scotiabank Arena", "43.6407", "-79.3791"),
                    new Location(0, "The AGO - Art Gallery of Ontario", "43.6536", "-79.3921"),
                    new Location(0, "Bloor-Yorkville", "43.6708", "-79.3940"),
                    new Location(0, "Museum of Contemporary Art Toronto", "43.6636", "-79.4231"),
                    new Location(0, "Toronto Islands Beach", "43.6110", "-79.3071"),
                    new Location(0, "Toronto Skateboard Park", "43.6500", "-79.3739"),
                    new Location(0, "Centre Island", "43.6170", "-79.3090"),
                    new Location(0, "The ROM (Royal Ontario Museum)", "43.6677", "-79.3948"),
                    new Location(0, "The Royal Conservatory of Music", "43.6677", "-79.3942"),
                    new Location(0, "Toronto Sculpture Garden", "43.6490", "-79.3761"),
                    new Location(0, "The Aga Khan Museum", "43.7253", "-79.3425"),
                    new Location(0, "Toronto International Film Festival (TIFF) Bell Lightbox", "43.6460", "-79.3865"),
                    new Location(0, "Toronto Waterfront", "43.6415", "-79.3802"),
                    new Location(0, "Scarborough Town Centre", "43.7736", "-79.2587"),
                    new Location(0, "Yorkville Village", "43.6714", "-79.3947"),
                    new Location(0, "Hockey Hall of Fame", "43.6435", "-79.3805"),
                    new Location(0, "The PATH", "43.6500", "-79.3800"),
                    new Location(0, "Roncesvalles Village", "43.6535", "-79.4555"),
                    new Location(0, "Fort York National Historic Site", "43.6410", "-79.4091"),
                    new Location(0, "Bluffer's Park and Beach", "43.7113", "-79.2437"),
                    new Location(0, "Toronto Symphony Orchestra at Roy Thomson Hall", "43.6443", "-79.3873"),
                    new Location(0, "Kew Gardens", "43.6744", "-79.2902"),
                    new Location(0, "Toronto Railway Heritage Centre", "43.6482", "-79.3738"),
                    new Location(0, "The Second City Toronto", "43.6496", "-79.3819"),
                    new Location(0, "Toronto Skate Park", "43.6522", "-79.3710"),
                    new Location(0, "Queen's Park", "43.6543", "-79.3823"),
                    new Location(0, "Harbourfront Canoe & Kayak Centre", "43.6409", "-79.3785"),
                    new Location(0, "St. James Park", "43.6484", "-79.3736"),
                    new Location(0, "Trinity Bellwoods Park", "43.6461", "-79.4196"),
                    new Location(0, "Massey Hall", "43.6552", "-79.3800"),
                    new Location(0, "Bloor Street West", "43.6671", "-79.4007"),
                    new Location(0, "Toronto Film Festival (TIFF) Central", "43.6459", "-79.3864"),
                    new Location(0, "Toronto Don Valley Parkway", "43.6750", "-79.3300"),
                    new Location(0, "Ontario Legislative Building", "43.6531", "-79.3804"),
                    new Location(0, "St. Lawrence Market North", "43.6486", "-79.3745"),
                    new Location(0, "Toronto Wildlife Centre", "43.7080", "-79.4391"),
                    new Location(0, "Trinity Bellwoods Park", "43.6461", "-79.4196"),
                    new Location(0, "The Ontario College of Art and Design", "43.6519", "-79.3928"),
                    new Location(0, "Liberty Village", "43.6377", "-79.4192"),
                    new Location(0, "Bloor-Yorkville", "43.6708", "-79.3940"),
                    new Location(0, "The Waterfront Trail", "43.6403", "-79.3806"),
                    new Location(0, "Toronto History Museum", "43.6538", "-79.3826"),
                    new Location(0, "Toronto Botanical Garden", "43.7330", "-79.3585"),
                    new Location(0, "Woodbine Park", "43.6690", "-79.3000"),
                    new Location(0, "Royal St. George's Golf Club", "43.6665", "-79.3982"),
                    new Location(0, "The Beaches Boardwalk", "43.6757", "-79.2914"),
                    new Location(0, "Toronto City Airport (Billy Bishop Airport)", "43.6275", "-79.3972"),
                    new Location(0, "St. Michael's Cathedral", "43.6513", "-79.3750"),
                    new Location(0, "Toronto's Financial District", "43.6499", "-79.3800"),
                    new Location(0, "Toronto Science Centre", "43.7259", "-79.3050"),
                    new Location(0, "Toronto Music Garden", "43.6287", "-79.3790"),
                    new Location(0, "High Park Zoo", "43.6447", "-79.4635"),
                    new Location(0, "The Queen Street West Art District", "43.6465", "-79.4107"),
                    new Location(0, "Toronto Christmas Market (Distillery District)", "43.6500", "-79.3585"),
                    new Location(0, "Pioneer Village", "43.7733", "-79.5022"),
                    new Location(0, "Ontario Science Centre Planetarium", "43.7259", "-79.3050"),
                    new Location(0, "Eglinton Park", "43.7112", "-79.3975"),
                    new Location(0, "Toronto Island Park", "43.6110", "-79.3091"),
                    new Location(0, "Montgomery's Inn", "43.7101", "-79.6026"),
                    new Location(0, "Riverdale Farm", "43.6678", "-79.3589")
            };

            for (Location location : initialLocations) {
                insertLocation(location);
            }
            initialLocationsInserted = true;
        }
    }
}
