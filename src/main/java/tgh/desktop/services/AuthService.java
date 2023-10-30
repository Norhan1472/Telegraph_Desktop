package tgh.desktop.services;

import java.net.SocketException;


import tgh.desktop.payload.request.LoginRequest;
import tgh.desktop.payload.response.LoginResponse;

public interface AuthService {

	public LoginResponse authenticateUser(LoginRequest loginRequest)throws SocketException;
	public void Logout();
	public String retUserName();

	
}
