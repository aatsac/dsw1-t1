// br/ufscar/dc/dsw/security/UsuarioDetails.java
package br.ufscar.dc.dsw.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.ufscar.dc.dsw.domain.Usuario;

@SuppressWarnings("serial")
public class UsuarioDetails implements UserDetails {

    private final Usuario usuario;

    public UsuarioDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String getUsername() {
        // agora usamos o e-mail como nome de usuário
        return usuario.getEmail();
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // o campo `papel` deve vir no formato "ROLE_XXX"
        return Collections.singletonList(
            new SimpleGrantedAuthority(usuario.getPapel())
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /** 
     * Se em algum lugar você precisar da entidade original 
     * (por exemplo para obter nome, CPF etc), use este getter.
     */
    public Usuario getUsuario() {
        return usuario;
    }
}
