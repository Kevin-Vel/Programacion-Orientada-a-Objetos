public class Cliente {
    private int idcli, dni;
    private String nom, apell, contraseña;

    public Cliente(int idclie, int dni, String nom, String apell) {
        this.idcli=idclie;
        this.nom=nom;
        this.apell=apell;
        this.dni=dni;
        this.contraseña=contraseña;
    }

    public String getContraseña(){return contraseña;}
    public String getNom() {return nom;}
    public String getApell(){return apell;}
    public int getIdcli(){return idcli;}
    public int getDni(){return dni;}

    public void setNom(String nom){this.nom=nom;}
    public void setApell(String apell){this.apell=apell;}
    public void setContraseña(String contraseña){this.contraseña=contraseña;}

    @Override
    public String toString(){
        return idcli + " " + dni + "-" + nom + "-" + apell;
    }
}


