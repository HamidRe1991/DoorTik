package com.smr.estate.Utils;

import com.smr.estate.Constant.Constant;

public class StringToId
{
    public static String Selling(String str)
    {
        if (str.equals(Constant.SALE))
            return Constant.SALE_ID;
        else if (str.equals(Constant.FULL_MORTGAGE))
            return Constant.FULL_MORTGAGE_ID;
        else if (str.equals(Constant.RENT))
            return Constant.RENT_ID;
        else if (str.equals(Constant.PRE_BUY))
            return Constant.PRE_BUY_ID;
        else if (str.equals(Constant.SWAP))
            return Constant.SWAP_ID;
        else if (str.equals(Constant.TAKING_PART))
            return Constant.TAKING_PART_ID;
        else
            return null;
    }

    public static String Possibilities(String str)
    {
        if (str.equals(Constant.ROOM))
            return Constant.ROOM_ID;
        else if (str.equals(Constant.WALL_CUPBOARD))
            return Constant.WALL_CUPBOARD_ID;
        else if (str.equals(Constant.KITCHEN))
            return Constant.KITCHEN_ID;
        else if (str.equals(Constant.BATHROOM))
            return Constant.BATHROOM_ID;
        else if (str.equals(Constant.WC_STATION))
            return Constant.WC_STATION_ID;
        else if (str.equals(Constant.WC))
            return Constant.WC_ID;
        else if (str.equals(Constant.TELEPHONE))
            return Constant.TELEPHONE_ID;
        else if (str.equals(Constant.PARKING))
            return Constant.PARKING_ID;
        else if (str.equals(Constant.WATER_METER))
            return Constant.WATER_METER_ID;
        else if (str.equals(Constant.ELECTRICITY_METER))
            return Constant.ELECTRICITY_METER_ID;
        else if (str.equals(Constant.GAS_METER))
            return Constant.GAS_METER_ID;
        else if (str.equals(Constant.WAREHOUSE))
            return Constant.WAREHOUSE_ID;
        else if (str.equals(Constant.PAINT))
            return Constant.PAINT_ID;
        else if (str.equals(Constant.TERRACE))
            return Constant.TERRACE_ID;
        else if (str.equals(Constant.IPHONE_VIDEO))
            return Constant.IPHONE_VIDEO_ID;
        else if (str.equals(Constant.ELEVATOR))
            return Constant.ELEVATOR_ID;
        else if (str.equals(Constant.ELECTRIC_DOOR))
            return Constant.ELECTRIC_DOOR_ID;
        else if (str.equals(Constant.WALLPAPER))
            return Constant.WALLPAPER_ID;
        else if (str.equals(Constant.SAUNA))
            return Constant.SAUNA_ID;
        else if (str.equals(Constant.SWIMMING_POOL))
            return Constant.SWIMMING_POOL_ID;
        else if (str.equals(Constant.WATER_COOLER))
            return Constant.WATER_COOLER_ID;
        else if (str.equals(Constant.GAS_COOLER))
            return Constant.GAS_COOLER_ID;
        else if (str.equals(Constant.PACKAGE))
            return Constant.PACKAGE_ID;
        else if (str.equals(Constant.WATER_HEATER))
            return Constant.WATER_HEATER_ID;
        else if (str.equals(Constant.RADIANT))
            return Constant.RADIANT_ID;
        else if (str.equals(Constant.FLOOR_HEATING))
            return Constant.FLOOR_HEATING_ID;
        else if (str.equals(Constant.AIR_CONDITIONER))
            return Constant.AIR_CONDITIONER_ID;
        else if (str.equals(Constant.CHILLER))
            return Constant.CHILLER_ID;
        else if (str.equals(Constant.PROTECTIVE_SHADE))
            return Constant.PROTECTIVE_SHADE_ID;
        else if (str.equals(Constant.BURGLAR_ALARM))
            return Constant.BURGLAR_ALARM_ID;
        else if (str.equals(Constant.GUARD))
            return Constant.GUARD_ID;
        else if (str.equals(Constant.ABATTOIR))
            return Constant.ABATTOIR_ID;
        else if (str.equals(Constant.SHOWCASE))
            return Constant.SHOWCASE_ID;
        else if (str.equals(Constant.SHELF))
            return Constant.SHELF_ID;
        else if (str.equals(Constant.WATER_WELL))
            return Constant.WATER_WELL_ID;
        else if (str.equals(Constant.FIRE_ALARM))
            return Constant.FIRE_ALARM_ID;
        else if (str.equals(Constant.FAN_COIL))
            return Constant.FAN_COIL_ID;
        else if (str.equals(Constant.RESTAURANT))
            return Constant.RESTAURANT_ID;
        else if (str.equals(Constant.PARTY_ROOM))
            return Constant.PARTY_ROOM_ID;
        else if (str.equals(Constant.SPORT_ROOM))
            return Constant.SPORT_ROOM_ID;
        else if (str.equals(Constant.LOBBY))
            return Constant.LOBBY_ID;
        else if (str.equals(Constant.COFFEE_SHOP))
            return Constant.COFFEE_SHOP_ID;
        else if (str.equals(Constant.FURNITURE))
            return Constant.FURNITURE_ID;
        else if (str.equals(Constant.KIDS_PARK))
            return Constant.KIDS_PARK_ID;
        else if (str.equals(Constant.FOUNTAIN))
            return Constant.FOUNTAIN_ID;
        else if (str.equals(Constant.AMUSEMENT))
            return Constant.AMUSEMENT_ID;
        else if (str.equals(Constant.CCTV))
            return Constant.CCTV_ID;
        else if (str.equals(Constant.YARD))
            return Constant.YARD_ID;
        else
            return null;
    }

    public static String Offer(String str)
    {
        if (str.equals(Constant.VIP))
            return Constant.VIP_ID;
        else if (str.equals(Constant.QUICK))
            return Constant.QUICK_ID;
        else if (str.equals(Constant.LUX))
            return Constant.LUX_ID;
        else
            return null;
    }

    public static String Category(String str)
    {
        if (str.equals(Constant.PENT_HOUSE))
            return Constant.PENT_HOUSE_ID;
        else if (str.equals(Constant.APARTMENT))
            return Constant.APARTMENT_ID;
        else if (str.equals(Constant.RESIDENTIAL_COMPLEX))
            return Constant.RESIDENTIAL_COMPLEX_ID;
        else if (str.equals(Constant.HOUSE))
            return Constant.HOUSE_ID;
        else if (str.equals(Constant.VILLAGE))
            return Constant.VILLAGE_ID;
        else if (str.equals(Constant.SUITES))
            return Constant.SUITES_ID;
        else if (str.equals(Constant.OFFICE))
            return Constant.OFFICE_ID;
        else if (str.equals(Constant.SHOP))
            return Constant.SHOP_ID;
        else if (str.equals(Constant.GARDEN))
            return Constant.GARDEN_ID;
        else if (str.equals(Constant.LAND_PLOTS))
            return Constant.LAND_PLOTS_ID;
        else if (str.equals(Constant.FARM))
            return Constant.FARM_ID;
        else if (str.equals(Constant.WORKSHOP))
            return Constant.WORKSHOP_ID;
        else if (str.equals(Constant.HALL))
            return Constant.HALL_ID;
        else if (str.equals(Constant.HOTEL))
            return Constant.HOTEL_ID;
        else if (str.equals(Constant.STORE))
            return Constant.STORE_ID;
        else if (str.equals(Constant.FACTORY))
            return Constant.FACTORY_ID;
        else
            return null;
    }

    public static String State(String str)
    {
        if (str.equals(Constant.ALBORZ))
            return Constant.ALBORZ_ID;
        else
            return null;
    }

    public static String City(String str)
    {
        if (str.equals(Constant.FARDIS))
            return Constant.FARDIS_ID;
        else
            return null;
    }

    public static String Area(String str)
    {
        if (str.equals(Constant.SELECT_AREA))
            return Constant.SELECT_AREA_ID;
        if (str.equals(Constant.SARHADDI))
            return Constant.SARHADDI_ID;
        else if (str.equals(Constant.EMAM_KHOMEINI))
            return Constant.EMAM_KHOMEINI_ID;
        else if (str.equals(Constant.NASTARAN))
            return Constant.NASTARAN_ID;
        else if (str.equals(Constant.CHEHELOHAST_DASTGAH))
            return Constant.CHEHELOHAST_DASTGAH_ID;
        else if (str.equals(Constant.BAHARAN))
            return Constant.BAHARAN_ID;
        else if (str.equals(Constant.MESHKIN_DASHT))
            return Constant.MESHKIN_DASHT_ID;
        else if (str.equals(Constant.MOHAMMAD_SHAHR))
            return Constant.MOHAMMAD_SHAHR_ID;
        else if (str.equals(Constant.KHOSHNAM))
            return Constant.KHOSHNAM_ID;
        else if (str.equals(Constant.DEHKADE))
            return Constant.DEHKADE_ID;
        else if (str.equals(Constant.SHAHRAKE_NAZ))
            return Constant.SHAHRAKE_NAZ_ID;
        else if (str.equals(Constant.MANZARIE))
            return Constant.MANZARIE_ID;
        else if (str.equals(Constant.SHAHRAKE_SADODAH))
            return Constant.SHAHRAKE_SADODAH_ID;
        else if (str.equals(Constant.SHAHRAKE_TALEGHANI))
            return Constant.SHAHRAKE_TALEGHANI_ID;
        else if (str.equals(Constant.BOLVARE_EMAM_REZA))
            return Constant.BOLVARE_EMAM_REZA_ID;
        else if (str.equals(Constant.KHIABAN_EMAM_HOSSEIN))
            return Constant.KHIABAN_EMAM_HOSSEIN_ID;
        else if (str.equals(Constant.BOLVARE_BAYAT))
            return Constant.BOLVARE_BAYAT_ID;
        else if (str.equals(Constant.POLE_ARTESH))
            return Constant.POLE_ARTESH_ID;
        else if (str.equals(Constant.FALAKE_AVAL))
            return Constant.FALAKE_AVAL_ID;
        else if (str.equals(Constant.FALAKE_DOVOM))
            return Constant.FALAKE_DOVOM_ID;
        else if (str.equals(Constant.FALAKE_SEVOM))
            return Constant.FALAKE_SEVOM_ID;
        else if (str.equals(Constant.FALAKE_CHAHAROM))
            return Constant.FALAKE_CHAHAROM_ID;
        else if (str.equals(Constant.FALAKE_PANJOM))
            return Constant.FALAKE_PANJOM_ID;
        else if (str.equals(Constant.SHAHRAKE_SEPAH))
            return Constant.SHAHRAKE_SEPAH_ID;
        else if (str.equals(Constant.SHAHRAKE_VAHDAT))
            return Constant.SHAHRAKE_VAHDAT_ID;
        else if (str.equals(Constant.SHAHRAKE_SIMIN_DASHT))
            return Constant.SHAHRAKE_SIMIN_DASHT_ID;
        else if (str.equals(Constant.SHAHRAK_HAFEZIE))
            return Constant.SHAHRAK_HAFEZIE_ID;
        else if (str.equals(Constant.ROKN_ABAD))
            return Constant.ROKN_ABAD_ID;
        else if (str.equals(Constant.SHAHRAKE_ERAM))
            return Constant.SHAHRAKE_ERAM_ID;
        else if (str.equals(Constant.KHIABAN_AHARI))
            return Constant.KHIABAN_AHARI_ID;
        else if (str.equals(Constant.SHAHRAKE_GOLESTAN))
            return Constant.SHAHRAKE_GOLESTAN_ID;
        else if (str.equals(Constant.SHAHRAKE_PARNIAN))
            return Constant.SHAHRAKE_PARNIAN_ID;
        else if (str.equals(Constant.SHAHRAKE_TABAN))
            return Constant.SHAHRAKE_TABAN_ID;
        else if (str.equals(Constant.SHAHRAKE_MAHAN))
            return Constant.SHAHRAKE_MAHAN_ID;
        else if (str.equals(Constant.SHAHRAKE_ALZAHRA))
            return Constant.SHAHRAKE_ALZAHRA_ID;
        else if (str.equals(Constant.SHAHRAKE_PASDAR))
            return Constant.SHAHRAKE_PASDAR_ID;
        else if (str.equals(Constant.NAJAF_ABAD))
            return Constant.NAJAF_ABAD_ID;
        else if (str.equals(Constant.SHAHRAKE_GOLHA))
            return Constant.SHAHRAKE_GOLHA_ID;
        else if (str.equals(Constant.KANALE_FARDIS))
            return Constant.KANALE_FARDIS_ID;
        else
            return null;
    }
}
