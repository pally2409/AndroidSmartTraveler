package com.example.pallaksingh.androidsmarttraveler;

public class UserInformation {

    public boolean amusement_park;
    public boolean library;
    public boolean art_gallery;
    public boolean museum;
    public boolean movie_theatre;
    public boolean shopping_mall;
    public boolean zoo;
    public boolean aquarium;
    public long unixTime;

    public UserInformation(boolean amusement_park, boolean library, boolean art_gallery, boolean museum, boolean movie_theatre, boolean shopping_mall, boolean zoo, boolean aquarium, long unixTime) {
        this.amusement_park = amusement_park;
        this.library = library;
        this.art_gallery = art_gallery;
        this.museum = museum;
        this.movie_theatre = movie_theatre;
        this.shopping_mall = shopping_mall;
        this.zoo = zoo;
        this.aquarium = aquarium;
        this.unixTime = unixTime;
    }
}
