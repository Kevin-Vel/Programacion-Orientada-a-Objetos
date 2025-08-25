public class Cliente {
    private int idcli, dni;
    private String nom, apell;

    public Cliente(int idclie, int dni, String nom, String apell) {
        this.idcli=idclie;
        this.nom=nom;
        this.apell=apell;
        this.dni=dni;
    }

    public String getNom() {return nom;}
    public String getApell(){return apell;}
    public int getIdcli(){return idcli;}
    public int getDni(){return dni;}

    public void setNom(String nom){this.nom=nom;}
    public void setApell(String apell){this.apell=apell;}

    @Override
    public String toString(){
        return idcli + " " + dni + "-" + nom + "-" + apell;
    }
}


