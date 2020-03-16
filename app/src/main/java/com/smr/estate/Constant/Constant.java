package com.smr.estate.Constant;

public class Constant
{
    //Server address
    public static final String BASE_URL = "http://89.42.211.54/";
    public static final String IMAGE_URL = BASE_URL + "storage/";

    //*************************************************************************//

    public static final int REQUEST_SELECT_ADS_IMAGE = 310;
    public static final int REQUEST_SELECT_PROFILE_IMAGE = 321;
    public static final int REQUEST_SELECT_ALLOWED_IMAGE = 338;
    public static final int REQUEST_SELECT_CITY = 414;
    public static final int REQUEST_SELECT_LOCATION = 424;
    public static final int REQUEST_SELECT_CATEGORY = 433;
    public static final int REQUEST_SELECT_AREA = 441;

    public static final int REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE_FOR_ADS = 513;
    public static final int REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE_FOR_PROFILE = 523;
    public static final int REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE_FOR_ALLOWED = 534;
    public static final int REQUEST_PERMISSIONS_LOCATION = 542;

    public static final int RESULT_OK = -1;

    public static final int REQUEST_FILTER_HOME_FRAGMENT = 614;
    public static final int REQUEST_DELETE_ADS = 628;

    public static final int IMAGE_MAX_SIZE = 600;

    //Total
    public static final String KEY_ID = "id";
    public static final String USER_ID = "user_id";
    public static final String ROLE = "role";
    public static final String PAGE = "page";
    public static final String PROVINCE = "province";
    public static final String SEARCH_TEXT = "searchString";
    public static final String TELL = "tel";
    public static final String KEY_NO_IMAGE = "NO_IMAGE";
    //.....................................................//

    //Register Or Update
    public static final String UPDATE = "update";
    public static final String INSERT = "insert";
    public static final String REQUEST_TYPE = "request_type";
    public static final String LICENSE_IMAGE = "licenseImage";
    public static final String MOBILE = "mobile";
    //.....................................................//

    //Checked Permission
    public static final String IS_IMAGE = "image";
    public static final String IS_PUBLIC = "status";
    public static final String IS_EXPIRE = "expire";
    //.....................................................//

    //Profile
    public static final String MAIL = "mail";
    public static final String IMG_PROFILE_CHECK = IMAGE_URL + "avatar/avatar.png";
    //.....................................................//

    //Note
    public static final String KEY_EVENT_DATE = "event_date";
    public static final String KEY_EVENT = "event";
    public static final String KEY_NOTE_ID = "event_id";
    public static final String KEY_NOTE = "note";
    public static final String KEY_NOTE_CHECK = "checkNote";
    //.....................................................//

    //Detail Post
    public static final String ESTATE_ID = "id";
    public static final String KEY_POST_ID = "postId";
    public static final String KEY_TITLE = "title";
    public static final String KEY_PRICE = "price";
    public static final String KEY_TIME = "time";
    public static final String KEY_CAT = "cat";
    public static final String KEY_TYPE = "type";
    public static final String KEY_IS_EXPIRE = "isExpire";
    public static final String KEY_IMAGE1 = "image1";
    public static final String KEY_IMAGE2 = "image2";
    public static final String KEY_IMAGE3 = "image3";
    public static final String KEY_IMAGE4 = "image4";
    public static final String KEY_IMAGE5 = "image5";
    public static final String IMG_CHECK = IMAGE_URL + "image/house.png";
    //.....................................................//

    //Filter activity
    public static final String KEY_FILTER_DATA_JSON = "filterDataJson";
    public static final String KEY_SELECTED_ID = "selectedId";
    //.....................................................//

    //Restore
    public static final String KEY_DRAFT_CHECK = "checkDraft";
    public static final String KEY_DRAFT = "draft";
    public static final String KEY_DRAFT_POSS = "draftPoss";
    public static final String KEY_SET_ADS = "set_ads";
    public static final String KEY_SELLING_ID = "sell_id";
    //.....................................................//

    //Post Item
    public static final String NAME = "name";
    public static final String LAST_NAME = "last_name";
    public static final String CITY = "city";
    public static final String STATES_ID = "states_id";
    public static final String CONSULT_STATE_NAME = "state_name";
    public static final String PASS = "pass";
    public static final String BREADTH = "breadth";
    public static final String PRICE = "price";
    public static final String RENT_PRICE = "rent";
    public static final String GEO_LAT = "geo_lat";
    public static final String GEO_LON = "geo_lon";
    public static final String ADDRESS = "add";
    public static final String AREA = "area";
    public static final String CONS_ID = "cons_id";
    public static final String AGENT_ID = "agent_id";
    public static final String SELLER_NAME = "seller_name";
    public static final String SELLER_TELL = "tel";
    public static final String IMAGE = "image";
    public static final String BUILT_IN = "built_in";
    public static final String DESCRIPTION = "discription";
    public static final String TYPE = "type";
    public static final String STATUS = "status";
    public static final String OFFICIAL_DOCUMENT = "official_doc";
    public static final String EXPIRE = "expire";
    public static final String SELLING_TYPE = "selling_type";
    public static final String POSSIBILITIES = "possibilities";
    public static final String OFFER = "offer";
    //1
    public static final String OFFER1 = "offer";

    //.....................................................//

    //Selling Name
    public static final String SALE = "فروش";
    public static final String FULL_MORTGAGE = "رهن کامل";
    public static final String RENT = "رهن و اجاره";
    public static final String PRE_BUY = "پیش خرید";
    public static final String SWAP = "معاوضه";
    public static final String TAKING_PART = "مشارکت";
    //.....................................................//

    //Selling Id
    public static final String SALE_ID = "1";
    public static final String FULL_MORTGAGE_ID = "2";
    public static final String RENT_ID = "3";
    public static final String PRE_BUY_ID = "4";
    public static final String SWAP_ID = "5";
    public static final String TAKING_PART_ID = "6";
    //.....................................................//

    //Possibilities Name
    public static final String ROOM = "اتاق";
    public static final String WALL_CUPBOARD = "کمد دیواری";
    public static final String KITCHEN = "آشپرخانه";
    public static final String BATHROOM = "حمام";
    public static final String WC_STATION = "سرویس فرنگی";
    public static final String WC = "سرویس ایرانی";
    public static final String TELEPHONE = "خط تلفن";
    public static final String PARKING = "پارکینگ";
    public static final String WATER_METER = "کنتور آب";
    public static final String ELECTRICITY_METER = "کنتور برق";
    public static final String GAS_METER = "کنتور گاز";
    public static final String WAREHOUSE = "انبار";
    public static final String PAINT = "نقاشی";
    public static final String TERRACE = "تراس";
    public static final String IPHONE_VIDEO = "آیفون تصویری";
    public static final String ELEVATOR = "آسانسور";
    public static final String ELECTRIC_DOOR = "درب برقی";
    public static final String WALLPAPER = "کاغذ دیواری";
    public static final String SAUNA = "سونا";
    public static final String SWIMMING_POOL = "استخر";
    public static final String WATER_COOLER = "کولر آبی";
    public static final String GAS_COOLER = "کولر گازی";
    public static final String PACKAGE = "پکیج";
    public static final String WATER_HEATER = "آب گرمکن";
    public static final String RADIANT = "رادیاتور";
    public static final String FLOOR_HEATING = "گرمایش از کف";
    public static final String AIR_CONDITIONER = "تهویه هوا";
    public static final String CHILLER = "چیلر";
    public static final String PROTECTIVE_SHADE = "کرکره محافظ";
    public static final String BURGLAR_ALARM = "دزدگیر";
    public static final String GUARD = "نگهبان";
    public static final String ABATTOIR = "آبدارخانه";
    public static final String SHOWCASE = "ویترین";
    public static final String SHELF = "قفسه";
    public static final String WATER_WELL = "چاه آب";
    public static final String FIRE_ALARM = "اعلان حریق";
    public static final String FAN_COIL = "فن کوئل";
    public static final String RESTAURANT = "رستوران";
    public static final String PARTY_ROOM = "اتاق جشن";
    public static final String SPORT_ROOM = "اتاق ورزش";
    public static final String LOBBY = "لابی";
    public static final String COFFEE_SHOP = "کافه";
    public static final String FURNITURE = "مبلمان";
    public static final String KIDS_PARK = "پارک کودک";
    public static final String FOUNTAIN = "آبنما";
    public static final String AMUSEMENT = "سالن تفریحی";
    public static final String CCTV = "دوربین مداربسته";
    public static final String YARD = "حیاط";
    //.....................................................//

    //Possibilities Id
    public static final String ROOM_ID = "1";
    public static final String WALL_CUPBOARD_ID = "2";
    public static final String KITCHEN_ID = "3";
    public static final String BATHROOM_ID = "4";
    public static final String WC_STATION_ID = "5";
    public static final String WC_ID = "6";
    public static final String TELEPHONE_ID = "7";
    public static final String PARKING_ID = "8";
    public static final String WATER_METER_ID = "9";
    public static final String ELECTRICITY_METER_ID = "10";
    public static final String GAS_METER_ID = "11";
    public static final String WAREHOUSE_ID = "12";
    public static final String PAINT_ID = "13";
    public static final String TERRACE_ID = "14";
    public static final String IPHONE_VIDEO_ID = "15";
    public static final String ELEVATOR_ID = "16";
    public static final String ELECTRIC_DOOR_ID = "17";
    public static final String WALLPAPER_ID = "18";
    public static final String SAUNA_ID = "19";
    public static final String SWIMMING_POOL_ID = "20";
    public static final String WATER_COOLER_ID = "21";
    public static final String GAS_COOLER_ID = "22";
    public static final String PACKAGE_ID = "23";
    public static final String WATER_HEATER_ID = "24";
    public static final String RADIANT_ID = "25";
    public static final String FLOOR_HEATING_ID = "26";
    public static final String AIR_CONDITIONER_ID = "27";
    public static final String CHILLER_ID = "28";
    public static final String PROTECTIVE_SHADE_ID = "29";
    public static final String BURGLAR_ALARM_ID = "30";
    public static final String GUARD_ID = "31";
    public static final String ABATTOIR_ID = "32";
    public static final String SHOWCASE_ID = "33";
    public static final String SHELF_ID = "34";
    public static final String WATER_WELL_ID = "35";
    public static final String FIRE_ALARM_ID = "36";
    public static final String FAN_COIL_ID = "37";
    public static final String RESTAURANT_ID = "38";
    public static final String PARTY_ROOM_ID = "39";
    public static final String SPORT_ROOM_ID = "40";
    public static final String LOBBY_ID = "41";
    public static final String COFFEE_SHOP_ID = "42";
    public static final String FURNITURE_ID = "43";
    public static final String KIDS_PARK_ID = "44";
    public static final String FOUNTAIN_ID = "45";
    public static final String AMUSEMENT_ID = "46";
    public static final String CCTV_ID = "47";
    public static final String YARD_ID = "48";
    //.....................................................//

    //Offer Name
    public static final String VIP = "پیشنهاد ویژه";
    public static final String QUICK = "فوری";
    public static final String LUX = "لوکس";
    //.....................................................//

    //Offer Id
    public static final String VIP_ID = "1";
    public static final String QUICK_ID = "2";
    public static final String LUX_ID = "3";
    //.....................................................//

    //Category List Name
    public static final String PENT_HOUSE = "پنت هاوس";
    public static final String APARTMENT = "آپارتمان";
    public static final String RESIDENTIAL_COMPLEX = "مجتمع مسکونی";
    public static final String HOUSE = "خانه ویلایی";
    public static final String VILLAGE = "ویلا";
    public static final String SUITES = "سوئیت";
    public static final String OFFICE = "اداری";
    public static final String SHOP = "مغازه و تجاری";
    public static final String GARDEN = "باغ";
    public static final String LAND_PLOTS = "زمین ملکی";
    public static final String FARM = "زمین زراعی";
    public static final String WORKSHOP = "کارگاه";
    public static final String HALL = "سالن";
    public static final String HOTEL = "هتل";
    public static final String STORE = "انبار";
    public static final String FACTORY = "کارخانه";
    //.....................................................//

    //Category Id
    public static final String PENT_HOUSE_ID = "1";
    public static final String APARTMENT_ID = "2";
    public static final String RESIDENTIAL_COMPLEX_ID = "3";
    public static final String HOUSE_ID = "4";
    public static final String VILLAGE_ID = "5";
    public static final String SUITES_ID = "6";
    public static final String OFFICE_ID = "7";
    public static final String SHOP_ID = "8";
    public static final String GARDEN_ID = "9";
    public static final String LAND_PLOTS_ID = "10";
    public static final String FARM_ID = "11";
    public static final String WORKSHOP_ID = "12";
    public static final String HALL_ID = "13";
    public static final String HOTEL_ID = "14";
    public static final String STORE_ID = "15";
    public static final String FACTORY_ID = "16";
    //.....................................................//

    //State List Name
    public static final String ALBORZ = "البرز";
    //.....................................................//

    //State List Name
    public static final String ALBORZ_ID = "1";
    //.....................................................//

    //City List Name
    public static final String FARDIS = "فردیس";
    public static final String SELECT_CITY = "انتخاب شهر ...";
    //.....................................................//

    //City List ID
    public static final String FARDIS_ID = "1";
    public static final String SELECT_CITY_ID = "0";
    //.....................................................//

    //Area List Name
    public static final String SELECT_AREA = "انتخاب ناحیه ...";
    public static final String SARHADDI = "میدان سرحردی";
    public static final String EMAM_KHOMEINI = "میدان شانزده متری امام خمینی";
    public static final String NASTARAN = "نسترن شرقی و غربی";
    public static final String CHEHELOHAST_DASTGAH = "48 دستگاه";
    public static final String BAHARAN = "بلوار بهاران";
    public static final String MESHKIN_DASHT = "مشکین دشت";
    public static final String MOHAMMAD_SHAHR = "محمد شهر";
    public static final String KHOSHNAM = "خوشنام";
    public static final String DEHKADE = "دهکده";
    public static final String SHAHRAKE_NAZ = "شهرک ناز";
    public static final String MANZARIE = "منظریه";
    public static final String SHAHRAKE_SADODAH = "شهرک 110";
    public static final String SHAHRAKE_TALEGHANI = "شهرک طالقانی";
    public static final String BOLVARE_EMAM_REZA = "بلوار امام رضا";
    public static final String KHIABAN_EMAM_HOSSEIN = "خیابان امام حسین";
    public static final String BOLVARE_BAYAT = "بلوار بیات";
    public static final String POLE_ARTESH = "پل ارتش";
    public static final String FALAKE_AVAL = "فلکه اول";
    public static final String FALAKE_DOVOM = "فلکه دوم";
    public static final String FALAKE_SEVOM = "فلکه سوم";
    public static final String FALAKE_CHAHAROM = "فلکه چهارم";
    public static final String FALAKE_PANJOM = "فلکه پنجم";
    public static final String SHAHRAKE_SEPAH = "شهرک سپاه";
    public static final String SHAHRAKE_VAHDAT = "شهرک وحدت";
    public static final String SHAHRAKE_SIMIN_DASHT = "شهرک صنعتی سیمین دشت";
    public static final String SHAHRAK_HAFEZIE = "شهرک حافظیه";
    public static final String ROKN_ABAD = "رکن آباد";
    public static final String SHAHRAKE_ERAM = "شهرک ارم";
    public static final String KHIABAN_AHARI = "خیابان اهری";
    public static final String SHAHRAKE_GOLESTAN = "شهرک گلستان";
    public static final String SHAHRAKE_PARNIAN = "شهرک پرنیان";
    public static final String SHAHRAKE_TABAN = "شهرک تابان";
    public static final String SHAHRAKE_MAHAN = "شهرک ماهان";
    public static final String SHAHRAKE_ALZAHRA = "شهرک الزهرا";
    public static final String SHAHRAKE_PASDAR = "شهرک پاسدار";
    public static final String NAJAF_ABAD = "نجف آباد";
    public static final String SHAHRAKE_GOLHA = "شهرک گلها";
    public static final String KANALE_FARDIS = "کانال فردیس";
    //.....................................................//

    //Area List ID
    public static final String SELECT_AREA_ID = "0";
    public static final String SARHADDI_ID = "1";
    public static final String EMAM_KHOMEINI_ID = "2";
    public static final String NASTARAN_ID = "3";
    public static final String CHEHELOHAST_DASTGAH_ID = "4";
    public static final String BAHARAN_ID = "5";
    public static final String MESHKIN_DASHT_ID = "6";
    public static final String MOHAMMAD_SHAHR_ID = "7";
    public static final String KHOSHNAM_ID = "8";
    public static final String DEHKADE_ID = "9";
    public static final String SHAHRAKE_NAZ_ID = "10";
    public static final String MANZARIE_ID = "11";
    public static final String SHAHRAKE_SADODAH_ID = "12";
    public static final String SHAHRAKE_TALEGHANI_ID = "13";
    public static final String BOLVARE_EMAM_REZA_ID = "14";
    public static final String KHIABAN_EMAM_HOSSEIN_ID = "15";
    public static final String BOLVARE_BAYAT_ID = "16";
    public static final String POLE_ARTESH_ID = "17";
    public static final String FALAKE_AVAL_ID = "18";
    public static final String FALAKE_DOVOM_ID = "19";
    public static final String FALAKE_SEVOM_ID = "20";
    public static final String FALAKE_CHAHAROM_ID = "21";
    public static final String FALAKE_PANJOM_ID = "22";
    public static final String SHAHRAKE_SEPAH_ID = "23";
    public static final String SHAHRAKE_VAHDAT_ID = "24";
    public static final String SHAHRAKE_SIMIN_DASHT_ID = "25";
    public static final String SHAHRAK_HAFEZIE_ID = "26";
    public static final String ROKN_ABAD_ID = "27";
    public static final String SHAHRAKE_ERAM_ID = "28";
    public static final String KHIABAN_AHARI_ID = "29";
    public static final String SHAHRAKE_GOLESTAN_ID = "30";
    public static final String SHAHRAKE_PARNIAN_ID = "31";
    public static final String SHAHRAKE_TABAN_ID = "32";
    public static final String SHAHRAKE_MAHAN_ID = "33";
    public static final String SHAHRAKE_ALZAHRA_ID = "34";
    public static final String SHAHRAKE_PASDAR_ID = "35";
    public static final String NAJAF_ABAD_ID = "36";
    public static final String SHAHRAKE_GOLHA_ID = "37";
    public static final String KANALE_FARDIS_ID = "38";
    //.....................................................//
}
