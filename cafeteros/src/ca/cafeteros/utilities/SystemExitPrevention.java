package ca.cafeteros.utilities;

import java.security.Permission;

public class SystemExitPrevention extends SecurityManager {
	
	private boolean isExitAllowedFlag;
	
	public SystemExitPrevention(){
		super();
		this.isExitAllowedFlag = false;
	}
	
	public boolean getIsExitAllowedFlag(){
		return this.isExitAllowedFlag;
	}
	
	@Override
    public void checkPermission(Permission perm) {
        // allow anything.
    }
	
	@Override
    public void checkPermission(Permission perm, Object context) {
        // allow anything.
    }
	
	@Override
	public void checkExit(int status){
		if(!this.getIsExitAllowedFlag()){
			throw new SecurityException();
		}
		super.checkExit(status);
	}
	
	public void setIsExitAllowed(boolean f){
		this.isExitAllowedFlag = f;
	}
}
