package rem.hw02;

import static rem.hw02.MyAgent.getObjectSize;

public class Measure {
    private static String MEASURE = "Measure with %s mechanism for %d object(s) with type %s composed %d bytes";
    private static String MECH_MEMORY = "memory";
    private static String INSTR_MEMORY = "instrument";

    private AbstractObjectFactory abstractObjectFactory;

    public Measure(AbstractObjectFactory abstractObjectFactory) {
        this.abstractObjectFactory = abstractObjectFactory;
    }

    public Report measureAbstractFactory() throws InterruptedException {
        Report report = new Report();
        long memoryBefore = 0, memoryAfter = 0;
        memoryBefore = getMemory();
        Object emptyOne = abstractObjectFactory.getEmptyOne();
        memoryAfter = getMemory();
        fillObjectReport(report, emptyOne, memoryBefore, memoryAfter);

        memoryBefore = getMemory();
        Object filledOne = abstractObjectFactory.getFilledOne();
        memoryAfter = getMemory();
        fillObjectReport(report, filledOne, memoryBefore, memoryAfter);

        memoryBefore = getMemory();
        Object[] referenceArray = abstractObjectFactory.getReferenceArray();
        memoryAfter = getMemory();
        fillObjectsReport(report, referenceArray, memoryBefore, memoryAfter);

        memoryBefore = getMemory();
        Object[] filledArray = abstractObjectFactory.getFilledArray();
        memoryAfter = getMemory();
        fillObjectsReport(report, filledArray, memoryBefore, memoryAfter);

        return report;
    }

    private void fillObjectReport(Report report, Object object, long memoryBefore, long memoryAfter) {
        fillReport(report, object, 1, memoryBefore, memoryAfter);
    }

    private void fillObjectsReport(Report report, Object[] object, long memoryBefore, long memoryAfter) {
        fillReport(report, object, object.length, memoryBefore, memoryAfter);
    }

    private void fillReport(Report report, Object object, int size, long memoryBefore, long memoryAfter) {
        report.addMessage(String.format(MEASURE, MECH_MEMORY, size, object.getClass().getCanonicalName(), difference(memoryBefore, memoryAfter, size)));
        report.addMessage(String.format(MEASURE, INSTR_MEMORY, size, object.getClass().getCanonicalName(), getObjectSize(object) / size));
    }

    private static long difference(long measureBefore, long measureAfter, int size) {
        return (measureAfter - measureBefore) / size;
    }

    private static long getMemory() throws InterruptedException {
        System.gc();
        Thread.sleep(50);
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
