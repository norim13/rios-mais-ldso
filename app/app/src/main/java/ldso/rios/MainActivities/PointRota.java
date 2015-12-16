package ldso.rios.MainActivities;

/**
 * Created by filipe on 16/12/2015.
 */
public class PointRota {
    Integer id;
    String nome;
    String descricao;
    Float lat,lon;
    Integer ordem;
    Integer route_id;

    public Float getLat() {
        return lat;
    }

    public Float getLon() {
        return lon;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public Integer getRoute_id() {
        return route_id;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public PointRota(Integer id, String nome, String descricao, Float lat, Float lon, Integer ordem, Integer route_id) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.lat = lat;
        this.lon = lon;
        this.ordem = ordem;
        this.route_id = route_id;
    }
}
