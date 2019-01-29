package rem.hw02;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class MyAgent implements ClassFileTransformer {
	
	static Instrumentation instrumentation = null;
	
    public static void premain(String agentArgs, Instrumentation inst) {
        initialize(agentArgs, inst, true);
    }
    
    public static void agentmain(String agentArgs, Instrumentation inst) {
        initialize(agentArgs, inst, false);
    }
	
	public static void initialize(String agentArgs, Instrumentation inst, boolean isPremain) {
	    MyAgent.instrumentation = inst;
	    inst.addTransformer(new MyAgent(), true);
	}

    public byte[] transform(ClassLoader loader, String className,
            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
            byte[] classfileBuffer) throws IllegalClassFormatException {

        System.out.println("Basic Agent: " + className + " : " + loader);
        
        return null;
    }

    public static long getObjectSize(Object o) {
        return instrumentation.getObjectSize(o);
    }
}
