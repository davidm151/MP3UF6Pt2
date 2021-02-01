/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.db4o.query.Query;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Properties;
import java.util.TreeSet;
import view.View;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author profe
 */
public class Model implements Serializable {

    private static View view;
    private static Collection<Equip> dades = new TreeSet<>();
    private static Collection<Equip> dades2 = new TreeSet<>(new EquipOrdenaPuntuacio());
    private static Collection<Jugador> dadesJugador = new TreeSet<>();
    private static Collection<Jugador> dadesJugador2 = new TreeSet<>(new JugadorOrdena());
    private static Properties properties = new Properties();
    private static Statement statement = null;
    private static Connection conn;
    private static PreparedStatement preparedStatement = null;
    private static ObjectContainer db;
    private static Collection<Jugador> dadesSql = new ArrayList<>();
    private static Collection<Jugador> dadesSqlSoda = new ArrayList<>();

    public static Collection<Jugador> getDadesSqlSoda() {
        return dadesSqlSoda;
    }

    public static void setDadesSqlSoda(Collection<Jugador> dadesSqlSoda) {
        Model.dadesSqlSoda = dadesSqlSoda;
    }

    public static Collection<Jugador> getDadesSql() {
        return dadesSql;
    }

    public static void setDadesSql(Collection<Jugador> dadesSql) {
        Model.dadesSql = dadesSql;
    }

    public static Collection<Equip> getDades() {
        return dades;
    }

    public static Collection<Equip> getDades2() {
        return dades2;
    }

    public static Collection<Jugador> getDadesJugador() {
        return dadesJugador;
    }

    public static Collection<Jugador> getDadesJugador2() {
        return dadesJugador2;
    }

    public Model() {
    }

    public void llegirEquip() throws IOException {
        File file1 = new File("equips.yap");
        String fullPath = file1.getAbsolutePath();
        db = Db4oEmbedded.openFile(fullPath);
        ObjectSet<Equip> os = db.queryByExample(new Equip(null, 0, 0, 0, 0, 0, 0, 0));
        Equip obj;
        for (Equip p : os) {
            System.out.println(p.get1_nom());
            Model.<Equip>insertar(p, Model.getDades());
        }

    }

    public void llegirJugador() throws IOException {
        ObjectSet<Jugador> os = db.queryByExample(new Jugador(null, null, null, 0, 0));
        Jugador obj;
        for (Jugador p : os) {
            if (p.get2_equip() != null) {
                for (Equip value : Model.dades) {
                    if (value.get1_nom().equals(p.get2_equip().get1_nom())) {
                        value._9_jug.add(p);
                    }
                }
            }
            Model.<Jugador>insertar(p, Model.getDadesJugador());
        }
    }

    public static <T> void insertar(T eq1, Collection<T> coleccion) {
        coleccion.add(eq1);
    }

    public static void borrarEquip(Equip eq1) {
        db.delete(eq1);
        for (Jugador j : eq1.get9_jug()) {
            j.set2_equip(null);
            db.store(j);
        }
        dades.remove(eq1);
        dades2.remove(eq1);
    }

    public static void borrarJugador(Jugador j1) {
        db.delete(j1);
        if (j1.get2_equip() != null) {
            j1.get2_equip().get9_jug().remove(j1);
        }
        dadesJugador.remove(j1);
        dadesJugador2.remove(j1);
    }

    public static Equip obtenirEquip2(String _1_nomEquip, int _2_golsEnContra, int _3_golsAfavor, int _4_partitsGuanyats, int _5_partitsPerduts, int _6_partitsEmpatats, int _7_puntsEquip, int _8_jornada) {
        Equip eq1 = new Equip(
                _1_nomEquip,
                _2_golsEnContra,
                _3_golsAfavor,
                _4_partitsGuanyats,
                _5_partitsPerduts,
                _6_partitsEmpatats,
                _7_puntsEquip,
                _8_jornada
        );
        db.store(eq1);
        Model.insertar(eq1, dades);
        Model.insertar(eq1, dades2);
        return eq1;
    }

    public static Jugador obtenirJugador(String _1_nomcognomsJugador, Equip _2_equipJugador, String _3_posicioJugador, int _4_golsJugador, int _5_partitsJugador) {
        Jugador jug1 = new Jugador(
                _1_nomcognomsJugador,
                _2_equipJugador,
                _3_posicioJugador,
                _4_golsJugador,
                _5_partitsJugador
        );
        db.store(jug1);
        Model.insertar(jug1, dadesJugador);
        Model.insertar(jug1, dadesJugador);
        return jug1;

    }

    public static void updateJugador(Jugador j1, Equip eq1, String nom, String nomActualitzat, String nomEquip, String posicio, int gols, int partits) {
        j1.set1_nomcognoms(nomActualitzat);
        db.store(j1);
        j1.set2_equip(eq1);
        db.store(j1);
        j1.set3_posicio(posicio);
        db.store(j1);
        j1.set4_gols(gols);
        db.store(j1);
        j1.set5_partits(partits);
        db.store(j1);

    }

    public static void updateEquip(Equip obj, String nomActualitzat, int gols_en_contra, int gols_afavor, int partits_guanyats, int partits_perduts, int partits_empatats, int punts, int jornada, String nom) {
        obj.set1_nom(nomActualitzat);
        db.store(obj);
        obj.set2_golsEnContra(gols_en_contra);
        db.store(obj);
        obj.set3_golsAfavor(gols_afavor);
        db.store(obj);
        obj.set4_partitsGuanyats(partits_guanyats);
        db.store(obj);
        obj.set5_partitsPerduts(partits_perduts);
        db.store(obj);
        obj.set6_partitsEmpatats(partits_empatats);
        db.store(obj);
        obj.set7_punts(punts);
        db.store(obj);
        obj.set8_jornada(jornada);
        db.store(obj);
    }

    public static void sqlNative(Equip eq1) {
        dadesSql.clear();
        ObjectSet<Jugador> os = db.query(new Predicate<Jugador>() {
            @Override
            public boolean match(Jugador et) {
                return et.get2_equip().toString().equals(eq1.get1_nom());
            }
        },
                new JugadorOrdena2()
        );
        for (Jugador p : os) {
            Model.insertar(p, dadesSql);
        }
    }

    public static void sqlSoda(String posicio) {
        dadesSqlSoda.clear();
        Comparator<Jugador> cmp = new Comparator<Jugador>() {
            public int compare(Jugador o1, Jugador o2) {
                return o1.get4_gols() - o2.get4_gols();
            }
        };
        Query query = db.query();
        query.constrain(Jugador.class);
        query.descend("_3_posicio").constrain(posicio);
        query.sortBy(cmp);
        ObjectSet<Jugador> result = query.execute();

        for (Jugador p : result) {
            System.out.println(p);
            Model.insertar(p, dadesSqlSoda);
        }
    }

    public static void tancarConn() {
        db.close();
    }
}

class EquipOrdenaPuntuacio implements Comparator<Equip> {

    @Override
    public int compare(Equip o1, Equip o2) {
        return o1.get1_nom().compareTo(o2.get1_nom());
    }

}

class JugadorOrdena implements Comparator<Jugador> {

    @Override
    public int compare(Jugador o1, Jugador o2) {
        return o1.get1_nomcognoms().compareTo(o2.get1_nomcognoms());
    }
}

class JugadorOrdena2 implements Comparator<Jugador> {

    @Override
    public int compare(Jugador o1, Jugador o2) {
        return o1.get5_partits()-o2.get5_partits();
    }
}
