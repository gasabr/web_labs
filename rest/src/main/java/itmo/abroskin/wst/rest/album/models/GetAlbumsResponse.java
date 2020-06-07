package itmo.abroskin.wst.rest.album.models;

import itmo.abroskin.wst.rest.models.AlbumDto;

import java.util.ArrayList;
import java.util.List;

public class GetAlbumsResponse {

    protected List<AlbumDto> albums;

    public void setAlbums(List<AlbumDto> albums) {
        this.albums = albums;
    }

    /**
     * Gets the value of the albums property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the albums property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlbums().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AlbumDto }
     * 
     * 
     */
    public List<AlbumDto> getAlbums() {
        if (albums == null) {
            albums = new ArrayList<AlbumDto>();
        }
        return this.albums;
    }

}
