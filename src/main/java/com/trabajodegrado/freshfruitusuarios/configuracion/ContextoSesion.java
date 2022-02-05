package com.trabajodegrado.freshfruitusuarios.configuracion;

public class ContextoSesion {
    
    private static ThreadLocal<Integer> actualUsuario = new ThreadLocal<>();

    public static void setUsuarioSesion(Integer usuario) {
    	actualUsuario.set(usuario);
    }

    public static Integer getUsuarioSesion() {
        return actualUsuario.get();
    }
    
}
