package com.example.android.materialdesigncodelab.domains;

public class RadioStation {
    private String id;
    private String url;
    private String name;
    private String homepage;
    private String favicon;
    private String tags;
    private String country;
    private String votes;
    private Boolean fav;
    private String langua;

    public RadioStation() {
    }

    public RadioStation(String id, String url, String name, String homepage, String favicon, String tags, String country, String votes, Boolean fav, String langua) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.homepage = homepage;
        this.favicon = favicon;
        this.tags = tags;
        this.country = country;
        this.votes = votes;
        this.fav = fav;
        this.langua = langua;
    }

    @Override
    public String toString() {
        return "RadioStation{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", homepage='" + homepage + '\'' +
                ", favicon='" + favicon + '\'' +
                ", tags='" + tags + '\'' +
                ", country='" + country + '\'' +
                ", votes='" + votes + '\'' +
                ", fav=" + fav +
                ", langua='" + langua + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public void setFav(Boolean fav) {
        this.fav = fav;
    }

    public Boolean getFav() {
        return fav;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public void setLangua(String langua) {
        this.langua = langua;
    }

    public String getHomepage() { return homepage; }

    public String getFavicon() {
        return favicon;
    }

    public String getTags() {
        return tags;
    }

    public String getCountry() {
        return country;
    }

    public String getVotes() {
        return votes;
    }

    public String getLangua() {
        return langua;
    }


}
