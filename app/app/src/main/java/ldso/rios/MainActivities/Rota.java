package ldso.rios.MainActivities;

import java.util.ArrayList;

/**
 * Created by filipe on 16/12/2015.
 */
public class Rota {
    Integer id;
    String nome;
    String descricao;
    String zona;
    String created_at,updated_at;
    Boolean publicada;

    ArrayList<PointRota> pontos;

    public Rota(Integer id, String nome, String descricao, String zona, String created_at, String updated_at, Boolean publicada) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.zona = zona;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.publicada = publicada;
        this.pontos=new ArrayList<PointRota>();
    }

    public void addPontos(Integer id, String nome, String descricao, Float lat, Float lon, Integer ordem, Integer route_id)
    {
        PointRota p= new PointRota(id,nome,descricao,lat,lon,ordem,route_id);
        pontos.add(p);
    }

    //GETTERS

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getZona() {
        return zona;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public Boolean getPublicada() {
        return publicada;
    }

    public ArrayList<PointRota> getPontos() {
        return pontos;
    }

}
