package com.map.hunter.helper;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import com.map.hunter.R;
import com.map.hunter.entity.Contributor;

public class ContributorsData {

    public static List<Contributor> getContributions(Context context){
        List<Contributor> contributors = new ArrayList<>();

        //-----> trips
        //itinerary
        Contributor contributor = new Contributor();
        contributor.setHasTitle(true);
        contributor.setMain_title("Developer Profile");
        contributor.setTitle("Developer Profile");
        contributor.setProject("OpenRouteService");
        contributor.setMap_url("https://github.com/hussein1000");
        contributors.add(contributor);
        //cycle_paths
        contributor = new Contributor();
        contributor.setHasTitle(false);
        contributor.setMain_title("Source Code");
        contributor.setTitle("Source Code");
        contributor.setProject("CyclOSM");
        contributor.setMap_url("https://github.com/hussein1000/map_hunter");
        contributors.add(contributor);

        //-----> life_skills
        //vegetarian_restaurants
        contributor = new Contributor();
        contributor.setHasTitle(true);
        contributor.setMain_title(context.getString(R.string.life_skills));
        contributor.setTitle(context.getString(R.string.vegetarian_restaurants));
        contributor.setProject("OpenVegeMap");
        contributor.setMap_url("https://openvegemap.netlib.re/");
        contributor.setSource_code("https://github.com/Rudloff/openvegemap/");
        contributor.setMaintainer("Pierre Rudloff");
        contributor.setDonations("https://liberapay.com/Rudloff/donate");
        contributors.add(contributor);
        //accessible_places
        contributor = new Contributor();
        contributor.setHasTitle(false);
        contributor.setMain_title(context.getString(R.string.life_skills));
        contributor.setTitle(context.getString(R.string.accessible_places));
        contributor.setProject("WheelMap");
        contributor.setMap_url("https://wheelmap.org/");
        contributor.setSource_code(context.getString(R.string.unknown));
        contributor.setMaintainer("SozialHelden");
        contributor.setDonations("https://sozialhelden.de/mitmachen/foerderer-werden/");
        contributors.add(contributor);
        //beer
        contributor = new Contributor();
        contributor.setHasTitle(false);
        contributor.setMain_title(context.getString(R.string.life_skills));
        contributor.setTitle(context.getString(R.string.beer));
        contributor.setProject("OpenBeerMap");
        contributor.setMap_url("https://openbeermap.github.io/");
        contributor.setSource_code("https://github.com/OpenBeerMap");
        contributor.setMaintainer("Noémie Lehuby");
        contributor.setDonations("https://flattr.com/@nlehuby");
        contributors.add(contributor);
        //solar_panel
        contributor = new Contributor();
        contributor.setHasTitle(false);
        contributor.setMain_title(context.getString(R.string.life_skills));
        contributor.setTitle(context.getString(R.string.solar_panel));
        contributor.setProject("OpenSolarMap");
        contributor.setMap_url("https://opensolarmap.org/");
        contributor.setSource_code("https://github.com/opensolarmap");
        contributor.setMaintainer("Michel Blancard");
        contributor.setDonations(context.getString(R.string.not_yet));
        contributors.add(contributor);
        //weather
        contributor = new Contributor();
        contributor.setHasTitle(false);
        contributor.setMain_title(context.getString(R.string.life_skills));
        contributor.setTitle(context.getString(R.string.weather));
        contributor.setProject("OpenWeatherMap");
        contributor.setMap_url("https://openweathermap.org/");
        contributor.setSource_code(context.getString(R.string.unknown));
        contributor.setMaintainer("Openweather Ltd.");
        contributor.setDonations(context.getString(R.string.not_yet));
        contributors.add(contributor);
        //qwant_map
        contributor = new Contributor();
        contributor.setHasTitle(false);
        contributor.setMain_title(context.getString(R.string.life_skills));
        contributor.setTitle(context.getString(R.string.qwant_map));
        contributor.setProject("QWANT MAPS");
        contributor.setMap_url("https://www.qwant.com/maps/");
        contributor.setSource_code("https://github.com/QwantResearch/qwantmaps/");
        contributor.setMaintainer("Qwant");
        contributor.setDonations(context.getString(R.string.not_yet));
        contributors.add(contributor);
        //recycle
        contributor = new Contributor();
        contributor.setHasTitle(false);
        contributor.setMain_title(context.getString(R.string.life_skills));
        contributor.setTitle(context.getString(R.string.recycle));
        contributor.setProject("OpenRecycleMap");
        contributor.setMap_url("https://openrecyclemap.org/map");
        contributor.setSource_code("https://github.com/meta-systems/openrecyclemap");
        contributor.setMaintainer("Meta-Systems");
        contributor.setDonations(context.getString(R.string.not_yet));
        contributors.add(contributor);
        //reduce_waste
        contributor = new Contributor();
        contributor.setHasTitle(false);
        contributor.setMain_title(context.getString(R.string.life_skills));
        contributor.setTitle(context.getString(R.string.reduce_waste));
        contributor.setProject("CartoVrac");
        contributor.setMap_url("https://cartovrac.fr");
        contributor.setSource_code("https://github.com/vivreanantes/cartovrac");
        contributor.setMaintainer("Mieux trier à Nantes");
        contributor.setDonations(context.getString(R.string.not_yet));
        contributors.add(contributor);
        //reduce_waste
        contributor = new Contributor();
        contributor.setHasTitle(false);
        contributor.setMain_title(context.getString(R.string.life_skills));
        contributor.setTitle(context.getString(R.string.colorize));
        contributor.setProject("Gribrouillon");
        contributor.setMap_url("https://gribrouillon.fr");
        contributor.setSource_code("https://framagit.org/PanierAvide/gribrouillon");
        contributor.setMaintainer("Adrien Pavie");
        contributor.setDonations("https://liberapay.com/PanierAvide");
        contributors.add(contributor);
        //queer_map
        contributor = new Contributor();
        contributor.setHasTitle(false);
        contributor.setMain_title(context.getString(R.string.life_skills));
        contributor.setTitle(context.getString(R.string.queer_map));
        contributor.setProject("QueerMap");
        contributor.setMap_url("https://map.qiekub.org/");
        contributor.setSource_code("https://github.com/qiekub");
        contributor.setMaintainer("Thomas Rosen");
        contributor.setDonations(context.getString(R.string.not_yet));
        contributors.add(contributor);
        //Water Map
        contributor = new Contributor();
        contributor.setHasTitle(false);
        contributor.setMain_title(context.getString(R.string.life_skills));
        contributor.setTitle(context.getString(R.string.water_map));
        contributor.setProject("Water Map");
        contributor.setMap_url("https://water-map.org/");
        contributor.setSource_code("https://github.com/EuropeanWaterProject");
        contributor.setMaintainer("NGO European Water Project");
        contributor.setDonations(context.getString(R.string.not_yet));
        contributors.add(contributor);

        //-----> hobbies
        //ski_snow
        contributor = new Contributor();
        contributor.setHasTitle(true);
        contributor.setMain_title(context.getString(R.string.hobbies));
        contributor.setTitle(context.getString(R.string.ski_snow));
        contributor.setProject("OpenSnowMap");
        contributor.setMap_url("http://opensnowmap.org/");
        contributor.setSource_code("https://github.com/yvecai?tab=repositories");
        contributor.setMaintainer("Yvecai");
        contributor.setDonations("http://opensnowmap.org/iframes/donate.html");
        contributors.add(contributor);
        //historic_places
        contributor = new Contributor();
        contributor.setHasTitle(false);
        contributor.setMain_title(context.getString(R.string.hobbies));
        contributor.setTitle(context.getString(R.string.historic_places));
        contributor.setProject("HistOsm");
        contributor.setMap_url("https://histosm.org/");
        contributor.setSource_code(context.getString(R.string.unknown));
        contributor.setMaintainer("Michael Auer - Chair of GIScience");
        contributor.setDonations(context.getString(R.string.not_yet));
        contributors.add(contributor);
        //french_breweries
        contributor = new Contributor();
        contributor.setHasTitle(false);
        contributor.setMain_title(context.getString(R.string.hobbies));
        contributor.setTitle(context.getString(R.string.french_breweries));
        contributor.setProject(" French Breweries");
        contributor.setMap_url("http://sp3r4z.fr/breweries/");
        contributor.setSource_code("https://framagit.org/Sp3r4z/carte-des-brasseries");
        contributor.setMaintainer("Sp3r4z");
        contributor.setDonations(context.getString(R.string.not_yet));
        contributors.add(contributor);


        //-----> regional_maps
        //breton
        contributor = new Contributor();
        contributor.setHasTitle(true);
        contributor.setMain_title(context.getString(R.string.regional_maps));
        contributor.setTitle(context.getString(R.string.breton));
        contributor.setProject("Kartenn");
        contributor.setMap_url("https://kartenn.openstreetmap.bzh");
        contributor.setSource_code(context.getString(R.string.unknown));
        contributor.setMaintainer(context.getString(R.string.unknown));
        contributor.setDonations(context.getString(R.string.not_yet));
        contributors.add(contributor);
        //occ_basq
        contributor = new Contributor();
        contributor.setHasTitle(false);
        contributor.setMain_title(context.getString(R.string.regional_maps));
        contributor.setTitle(context.getString(R.string.occ_basq));
        contributor.setProject(context.getString(R.string.unknown));
        contributor.setMap_url("https://tile.openstreetmap.fr/");
        contributor.setSource_code(context.getString(R.string.unknown));
        contributor.setMaintainer(context.getString(R.string.unknown));
        contributor.setDonations(context.getString(R.string.not_yet));
        contributors.add(contributor);


        //-----> regional_maps
        //contributions
        contributor = new Contributor();
        contributor.setHasTitle(true);
        contributor.setMain_title(context.getString(R.string.contributions));
        contributor.setTitle(context.getString(R.string.basic_map));
        contributor.setProject("OpenStreetMap");
        contributor.setMap_url("https://www.openstreetmap.org/");
        contributor.setSource_code("https://github.com/openstreetmap");
        contributor.setMaintainer("OSM Contributors");
        contributor.setDonations("https://openstreetmap.assoconnect.com/billetterie/offre/61684-j-faites-un-don-a-openstreetmap-france");
        contributors.add(contributor);
        //thematic_maps
        contributor = new Contributor();
        contributor.setHasTitle(false);
        contributor.setMain_title(context.getString(R.string.contributions));
        contributor.setTitle(context.getString(R.string.thematic_maps));
        contributor.setProject("MapContrib");
        contributor.setMap_url("https://www.mapcontrib.xyz/");
        contributor.setSource_code("https://github.com/mapcontrib/mapcontrib");
        contributor.setMaintainer("Guillaume Amat");
        contributor.setDonations(context.getString(R.string.not_yet));
        contributors.add(contributor);
        //billboard_advertises
        contributor = new Contributor();
        contributor.setHasTitle(false);
        contributor.setMain_title(context.getString(R.string.contributions));
        contributor.setTitle(context.getString(R.string.billboard_advertises));
        contributor.setProject("OpenAdvertMap");
        contributor.setMap_url("https://openadvertmap.pavie.info/");
        contributor.setSource_code("https://framagit.org/PanierAvide/OpenAdvertMap");
        contributor.setMaintainer("Adrien Pavie");
        contributor.setDonations("https://liberapay.com/PanierAvide");
        contributors.add(contributor);
        //interior_buildings
        contributor = new Contributor();
        contributor.setHasTitle(false);
        contributor.setMain_title(context.getString(R.string.contributions));
        contributor.setTitle(context.getString(R.string.interior_buildings));
        contributor.setProject("OpenLevelUp");
        contributor.setMap_url("https://openlevelup.net/");
        contributor.setSource_code("https://framagit.org/OpenLevelUp");
        contributor.setMaintainer("Adrien Pavie");
        contributor.setDonations("https://liberapay.com/PanierAvide");
        contributors.add(contributor);
        //then_and_now
        contributor = new Contributor();
        contributor.setHasTitle(false);
        contributor.setMain_title(context.getString(R.string.contributions));
        contributor.setTitle(context.getString(R.string.then_and_now));
        contributor.setProject("Then And Now");
        contributor.setMap_url("https://mvexel.github.io/thenandnow/");
        contributor.setSource_code("https://github.com/mvexel/thenandnow");
        contributor.setMaintainer("Martijn van Exel");
        contributor.setDonations(context.getString(R.string.not_yet));
        contributors.add(contributor);
        //hydrant
        contributor = new Contributor();
        contributor.setHasTitle(false);
        contributor.setMain_title(context.getString(R.string.contributions));
        contributor.setTitle(context.getString(R.string.hydrant));
        contributor.setProject("OsmHydrant");
        contributor.setMap_url("https://www.osmhydrant.org");
        contributor.setSource_code(context.getString(R.string.unknown));
        contributor.setMaintainer("Robert Koch");
        contributor.setDonations(context.getString(R.string.not_yet));
        contributors.add(contributor);
        //whatever
        contributor = new Contributor();
        contributor.setHasTitle(false);
        contributor.setMain_title(context.getString(R.string.contributions));
        contributor.setTitle(context.getString(R.string.whatever));
        contributor.setProject("OpenWhatEverMap");
        contributor.setMap_url("http://openwhatevermap.xyz/");
        contributor.setSource_code("https://github.com/Zverik/openwhatevermap");
        contributor.setMaintainer("Ilya Zverev");
        contributor.setDonations(context.getString(R.string.not_yet));
        contributors.add(contributor);
        //whatever
        contributor = new Contributor();
        contributor.setHasTitle(false);
        contributor.setMain_title(context.getString(R.string.contributions));
        contributor.setTitle(context.getString(R.string.whatever));
        contributor.setProject("OpenInfrastructureMap");
        contributor.setMap_url("https://openinframap.org/");
        contributor.setSource_code("https://github.com/openinframap/");
        contributor.setMaintainer("russss");
        contributor.setDonations("https://liberapay.com/russss/donate");
        contributors.add(contributor);
        //Mapcomplete
        contributor = new Contributor();
        contributor.setHasTitle(false);
        contributor.setMain_title(context.getString(R.string.contributions));
        contributor.setTitle(context.getString(R.string.mapcomplete));
        contributor.setProject("Mapcomplete");
        contributor.setMap_url("https://mapcomplete.osm.be");
        contributor.setSource_code("https://github.com/pietervdvn/MapComplete");
        contributor.setMaintainer("pietervdvn");
        contributor.setDonations(context.getString(R.string.not_yet));
        contributors.add(contributor);
        return contributors;
    }
}
