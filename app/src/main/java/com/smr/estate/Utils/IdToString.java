package com.smr.estate.Utils;

import com.smr.estate.Constant.Constant;

public class IdToString
{
    public static String Selling(String id)
    {
        if (id.equals(Constant.SALE_ID))
            return Constant.SALE;
        else if (id.equals(Constant.FULL_MORTGAGE_ID))
            return Constant.FULL_MORTGAGE;
        else if (id.equals(Constant.RENT_ID))
            return Constant.RENT;
        else if (id.equals(Constant.PRE_BUY_ID))
            return Constant.PRE_BUY;
        else if (id.equals(Constant.SWAP_ID))
            return Constant.SWAP;
        else if (id.equals(Constant.TAKING_PART_ID))
            return Constant.TAKING_PART;
        else
            return null;
    }

    public static String Possibilities(String id)
    {
        if (id.equals(Constant.ROOM_ID))
            return Constant.ROOM;
        else if (id.equals(Constant.WALL_CUPBOARD_ID))
            return Constant.WALL_CUPBOARD;
        else if (id.equals(Constant.KITCHEN_ID))
            return Constant.KITCHEN;
        else if (id.equals(Constant.BATHROOM_ID))
            return Constant.BATHROOM;
        else if (id.equals(Constant.WC_STATION_ID))
            return Constant.WC_STATION;
        else if (id.equals(Constant.WC_ID))
            return Constant.WC;
        else if (id.equals(Constant.TELEPHONE_ID))
            return Constant.TELEPHONE;
        else if (id.equals(Constant.PARKING_ID))
            return Constant.PARKING;
        else if (id.equals(Constant.WATER_METER_ID))
            return Constant.WATER_METER;
        else if (id.equals(Constant.ELECTRICITY_METER_ID))
            return Constant.ELECTRICITY_METER;
        else if (id.equals(Constant.GAS_METER_ID))
            return Constant.GAS_METER;
        else if (id.equals(Constant.WAREHOUSE_ID))
            return Constant.WAREHOUSE;
        else if (id.equals(Constant.PAINT_ID))
            return Constant.PAINT;
        else if (id.equals(Constant.TERRACE_ID))
            return Constant.TERRACE;
        else if (id.equals(Constant.IPHONE_VIDEO_ID))
            return Constant.IPHONE_VIDEO;
        else if (id.equals(Constant.ELEVATOR_ID))
            return Constant.ELEVATOR;
        else if (id.equals(Constant.ELECTRIC_DOOR_ID))
            return Constant.ELECTRIC_DOOR;
        else if (id.equals(Constant.WALLPAPER_ID))
            return Constant.WALLPAPER;
        else if (id.equals(Constant.SAUNA_ID))
            return Constant.SAUNA;
        else if (id.equals(Constant.SWIMMING_POOL_ID))
            return Constant.SWIMMING_POOL;
        else if (id.equals(Constant.WATER_COOLER_ID))
            return Constant.WATER_COOLER;
        else if (id.equals(Constant.GAS_COOLER_ID))
            return Constant.GAS_COOLER;
        else if (id.equals(Constant.PACKAGE_ID))
            return Constant.PACKAGE;
        else if (id.equals(Constant.WATER_HEATER_ID))
            return Constant.WATER_HEATER;
        else if (id.equals(Constant.RADIANT_ID))
            return Constant.RADIANT;
        else if (id.equals(Constant.FLOOR_HEATING_ID))
            return Constant.FLOOR_HEATING;
        else if (id.equals(Constant.AIR_CONDITIONER_ID))
            return Constant.AIR_CONDITIONER;
        else if (id.equals(Constant.CHILLER_ID))
            return Constant.CHILLER;
        else if (id.equals(Constant.PROTECTIVE_SHADE_ID))
            return Constant.PROTECTIVE_SHADE;
        else if (id.equals(Constant.BURGLAR_ALARM_ID))
            return Constant.BURGLAR_ALARM;
        else if (id.equals(Constant.GUARD_ID))
            return Constant.GUARD;
        else if (id.equals(Constant.ABATTOIR_ID))
            return Constant.ABATTOIR;
        else if (id.equals(Constant.SHOWCASE_ID))
            return Constant.SHOWCASE;
        else if (id.equals(Constant.SHELF_ID))
            return Constant.SHELF;
        else if (id.equals(Constant.WATER_WELL_ID))
            return Constant.WATER_WELL;
        else if (id.equals(Constant.FIRE_ALARM_ID))
            return Constant.FIRE_ALARM;
        else if (id.equals(Constant.FAN_COIL_ID))
            return Constant.FAN_COIL;
        else if (id.equals(Constant.RESTAURANT_ID))
            return Constant.RESTAURANT;
        else if (id.equals(Constant.PARTY_ROOM_ID))
            return Constant.PARTY_ROOM;
        else if (id.equals(Constant.SPORT_ROOM_ID))
            return Constant.SPORT_ROOM;
        else if (id.equals(Constant.LOBBY_ID))
            return Constant.LOBBY;
        else if (id.equals(Constant.COFFEE_SHOP_ID))
            return Constant.COFFEE_SHOP;
        else if (id.equals(Constant.FURNITURE_ID))
            return Constant.FURNITURE;
        else if (id.equals(Constant.KIDS_PARK_ID))
            return Constant.KIDS_PARK;
        else if (id.equals(Constant.FOUNTAIN_ID))
            return Constant.FOUNTAIN;
        else if (id.equals(Constant.AMUSEMENT_ID))
            return Constant.AMUSEMENT;
        else if (id.equals(Constant.CCTV_ID))
            return Constant.CCTV;
        else if (id.equals(Constant.YARD_ID))
            return Constant.YARD;
        else
            return null;
    }

    public static String Offer(String id)
    {
        if (id.equals(Constant.VIP_ID))
            return Constant.VIP;
        else if (id.equals(Constant.QUICK_ID))
            return Constant.QUICK;
        else if (id.equals(Constant.LUX_ID))
            return Constant.LUX;
        else
            return null;
    }

    public static String Category(String id)
    {
        if (id.equals(Constant.PENT_HOUSE_ID))
            return Constant.PENT_HOUSE;
        else if (id.equals(Constant.APARTMENT_ID))
            return Constant.APARTMENT;
        else if (id.equals(Constant.RESIDENTIAL_COMPLEX_ID))
            return Constant.RESIDENTIAL_COMPLEX;
        else if (id.equals(Constant.HOUSE_ID))
            return Constant.HOUSE;
        else if (id.equals(Constant.VILLAGE_ID))
            return Constant.VILLAGE;
        else if (id.equals(Constant.SUITES_ID))
            return Constant.SUITES;
        else if (id.equals(Constant.OFFICE_ID))
            return Constant.OFFICE;
        else if (id.equals(Constant.SHOP_ID))
            return Constant.SHOP;
        else if (id.equals(Constant.GARDEN_ID))
            return Constant.GARDEN;
        else if (id.equals(Constant.LAND_PLOTS_ID))
            return Constant.LAND_PLOTS;
        else if (id.equals(Constant.FARM_ID))
            return Constant.FARM;
        else if (id.equals(Constant.WORKSHOP_ID))
            return Constant.WORKSHOP;
        else if (id.equals(Constant.HALL_ID))
            return Constant.HALL;
        else if (id.equals(Constant.HOTEL_ID))
            return Constant.HOTEL;
        else if (id.equals(Constant.STORE_ID))
            return Constant.STORE;
        else if (id.equals(Constant.FACTORY_ID))
            return Constant.FACTORY;
        else
            return null;
    }

    public static String State(String id)
    {
        if (id.equals(Constant.ALBORZ_ID))
            return Constant.ALBORZ;
        else
            return null;
    }

    public static String City(String id)
    {
        if (id.equals(Constant.FARDIS_ID))
            return Constant.FARDIS;
        else
            return null;
    }

    public static String Area(String id)
    {
        if (id.equals(Constant.SARHADDI_ID))
            return Constant.SARHADDI;
        else if (id.equals(Constant.EMAM_KHOMEINI_ID))
            return Constant.EMAM_KHOMEINI;
        else if (id.equals(Constant.NASTARAN_ID))
            return Constant.NASTARAN;
        else if (id.equals(Constant.CHEHELOHAST_DASTGAH_ID))
            return Constant.CHEHELOHAST_DASTGAH;
        else if (id.equals(Constant.BAHARAN_ID))
            return Constant.BAHARAN;
        else if (id.equals(Constant.MESHKIN_DASHT_ID))
            return Constant.MESHKIN_DASHT;
        else if (id.equals(Constant.MOHAMMAD_SHAHR_ID))
            return Constant.MOHAMMAD_SHAHR;
        else if (id.equals(Constant.KHOSHNAM_ID))
            return Constant.KHOSHNAM;
        else if (id.equals(Constant.DEHKADE_ID))
            return Constant.DEHKADE;
        else if (id.equals(Constant.SHAHRAKE_NAZ_ID))
            return Constant.SHAHRAKE_NAZ;
        else if (id.equals(Constant.MANZARIE_ID))
            return Constant.MANZARIE;
        else if (id.equals(Constant.SHAHRAKE_SADODAH_ID))
            return Constant.SHAHRAKE_SADODAH;
        else if (id.equals(Constant.SHAHRAKE_TALEGHANI_ID))
            return Constant.SHAHRAKE_TALEGHANI;
        else if (id.equals(Constant.BOLVARE_EMAM_REZA_ID))
            return Constant.BOLVARE_EMAM_REZA;
        else if (id.equals(Constant.KHIABAN_EMAM_HOSSEIN_ID))
            return Constant.KHIABAN_EMAM_HOSSEIN;
        else if (id.equals(Constant.BOLVARE_BAYAT_ID))
            return Constant.BOLVARE_BAYAT;
        else if (id.equals(Constant.POLE_ARTESH_ID))
            return Constant.POLE_ARTESH;
        else if (id.equals(Constant.FALAKE_AVAL_ID))
            return Constant.FALAKE_AVAL;
        else if (id.equals(Constant.FALAKE_DOVOM_ID))
            return Constant.FALAKE_DOVOM;
        else if (id.equals(Constant.FALAKE_SEVOM_ID))
            return Constant.FALAKE_SEVOM;
        else if (id.equals(Constant.FALAKE_CHAHAROM_ID))
            return Constant.FALAKE_CHAHAROM;
        else if (id.equals(Constant.FALAKE_PANJOM_ID))
            return Constant.FALAKE_PANJOM;
        else if (id.equals(Constant.SHAHRAKE_SEPAH_ID))
            return Constant.SHAHRAKE_SEPAH;
        else if (id.equals(Constant.SHAHRAKE_VAHDAT_ID))
            return Constant.SHAHRAKE_VAHDAT;
        else if (id.equals(Constant.SHAHRAKE_SIMIN_DASHT_ID))
            return Constant.SHAHRAKE_SIMIN_DASHT;
        else if (id.equals(Constant.SHAHRAK_HAFEZIE_ID))
            return Constant.SHAHRAK_HAFEZIE;
        else if (id.equals(Constant.ROKN_ABAD_ID))
            return Constant.ROKN_ABAD;
        else if (id.equals(Constant.SHAHRAKE_ERAM_ID))
            return Constant.SHAHRAKE_ERAM;
        else if (id.equals(Constant.KHIABAN_AHARI_ID))
            return Constant.KHIABAN_AHARI;
        else if (id.equals(Constant.SHAHRAKE_GOLESTAN_ID))
            return Constant.SHAHRAKE_GOLESTAN;
        else if (id.equals(Constant.SHAHRAKE_PARNIAN_ID))
            return Constant.SHAHRAKE_PARNIAN;
        else if (id.equals(Constant.SHAHRAKE_TABAN_ID))
            return Constant.SHAHRAKE_TABAN;
        else if (id.equals(Constant.SHAHRAKE_MAHAN_ID))
            return Constant.SHAHRAKE_MAHAN;
        else if (id.equals(Constant.SHAHRAKE_ALZAHRA_ID))
            return Constant.SHAHRAKE_ALZAHRA;
        else if (id.equals(Constant.SHAHRAKE_PASDAR_ID))
            return Constant.SHAHRAKE_PASDAR;
        else if (id.equals(Constant.NAJAF_ABAD_ID))
            return Constant.NAJAF_ABAD;
        else if (id.equals(Constant.SHAHRAKE_GOLHA_ID))
            return Constant.SHAHRAKE_GOLHA;
        else if (id.equals(Constant.KANALE_FARDIS_ID))
            return Constant.KANALE_FARDIS;
        else
            return null;
    }
}
