package com.froad.security.entity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
public class UserDetailImpl extends User {
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 2356706529782700016L;
	
	public UserDetailImpl(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked, GrantedAuthority[] authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
		// TODO Auto-generated constructor stub
	}

}
