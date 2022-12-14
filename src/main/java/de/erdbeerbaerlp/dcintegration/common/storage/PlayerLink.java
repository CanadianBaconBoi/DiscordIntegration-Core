package de.erdbeerbaerlp.dcintegration.common.storage;

public class PlayerLink {
    public String discordID = "";
    public String mcPlayerUUID = "";
    public String floodgateUUID = "";
    public PlayerSettings settings = new PlayerSettings();
    public PlayerLink(){

    }
    public PlayerLink(String id, String mcPlayerUUID, String floodgateUUID, PlayerSettings settings) {
        this.mcPlayerUUID = mcPlayerUUID;
        this.floodgateUUID = floodgateUUID;
        this.discordID = id;
        this.settings = settings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerLink that = (PlayerLink) o;

        if (!discordID.equals(that.discordID)) return false;
        return mcPlayerUUID.equals(that.mcPlayerUUID);
    }

    @Override
    public int hashCode() {
        int result = discordID.hashCode();
        result = 31 * result + mcPlayerUUID.hashCode();
        return result;
    }
}
