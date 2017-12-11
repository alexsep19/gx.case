package gxt.server;


import com.google.web.bindery.requestfactory.shared.ServiceLocator;

public class DaoService implements ServiceLocator {
    private static Dao serviceInstance;
    
    @Override
    public Object getInstance(Class<?> clazz) {
	if (serviceInstance == null) serviceInstance = new Dao();
	return serviceInstance;
    }

	}

