package com.example.cse441_music.Model;

public class Song {
    private String id;
    private String name;
    private String duration;
    private String artistId;
    private String artistName;
    private String albumName;
    private String albumId;
    private String licenseUrl;
    private int position;
    private String releaseDate;
    private String albumImage;
    private String audioUrl;
    private String audioDownloadUrl;
    private String proUrl;
    private String shortUrl;
    private String shareUrl;
    private String waveform;
    private String imageUrl;
    private MusicInfo musicInfo;
    private boolean audioDownloadAllowed;

    public Song(String id, String name, String artistName, String audioUrl, String imageUrl) {
        this.id = id;
        this.name = name;
        this.artistName = artistName;
        this.audioUrl = audioUrl;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public static class MusicInfo {
        private String vocalInstrumental;
        private String lang;
        private String gender;
        private String acousticElectric;
        private String speed;
        private Tags tags;

        public String getVocalInstrumental() {
            return vocalInstrumental;
        }

        public String getLang() {
            return lang;
        }

        public String getGender() {
            return gender;
        }

        public String getAcousticElectric() {
            return acousticElectric;
        }

        public String getSpeed() {
            return speed;
        }

        public Tags getTags() {
            return tags;
        }

        public static class Tags {
            private String[] genres;
            private String[] instruments;
            private String[] varTags;

            public String[] getGenres() {
                return genres;
            }

            public String[] getInstruments() {
                return instruments;
            }

            public String[] getVarTags() {
                return varTags;
            }
        }
    }
}
