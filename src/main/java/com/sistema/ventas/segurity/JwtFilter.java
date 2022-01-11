package com.sistema.ventas.segurity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

	private static final Logger logger = Logger.getLogger(JwtFilter.class.getName());
	
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService service;


    @SuppressWarnings({ "unused", "unused" })
	@Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException{
	    try {
	        String authorizationHeader = httpServletRequest.getHeader("Authorization");
	        String[] arrServiciosSinSeguridad = {"/ventas/v1/usuarios/recuperarContrasena","/ventas/v1/autenticacion/refreshToken"};	
	        
	        String strToken = null;
	        String strUserName = null;
	        
	        if (!(httpServletRequest.getRequestURI()!=null && 
					Arrays.stream(arrServiciosSinSeguridad).anyMatch(StringUtils.upperCase(httpServletRequest.getRequestURI())::equalsIgnoreCase))) {
		        	
		        	 if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			            strToken = authorizationHeader.substring(7);
						strUserName = jwtUtil.extractUsername(strToken);
				     }
				
			        if (strUserName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			            UserDetails objUserDetails = service.loadUserByUsername(strUserName);

		                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
		                		objUserDetails, 
		    					null,
		    					new ArrayList<>());
		                usernamePasswordAuthenticationToken
		                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
		                
		                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

			        }
	        }  
		        
		} catch (Exception e) {
			e.printStackTrace();
			httpServletResponse.setStatus(Status.UNAUTHORIZED.getStatusCode());
			httpServletResponse.setHeader("msgAutorizadorException", e.getMessage());
		}
    
	    filterChain.doFilter(httpServletRequest, httpServletResponse);
	    
    }
}
