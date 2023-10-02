package Software;

import Hardware.Interrupts;

import java.util.ArrayList;

public class PCB {
    public int id;
    public Interrupts interrupt;
    public ArrayList<Integer> allocatedPages;

    // CPU context
    public int pc;
    public ProcessStatus status;
    public int[] reg;
    public int allocatedPartition;
    public PCB(int id, ArrayList<Integer> allocatedPages, int allocatedPartition, int pc) {
        this.allocatedPartition = allocatedPartition;
        this.allocatedPages = allocatedPages;
        this.id = id;
        this.interrupt = Interrupts.noInterrupt;
        this.pc = pc;
        this.status = ProcessStatus.READY;
        this.reg = new int[10];
    }

    public int getAllocatedPartition() {
        return this.allocatedPartition;
    }

    public void setAllocatedPartition(int partitionNumber) {
        this.allocatedPartition = partitionNumber;
    }

    //retorna a lista de paginas de um processo
    public ArrayList<Integer> getAllocatedPages() {
        return this.allocatedPages;
    }

    public int getId() {
        return this.id;
    }

    public String toString(){
        return "ID: " + id +
                "\tPages: " + allocatedPages +
                "\tProgram Counter: " + pc +
                "\tStatus: " + status +
                "\tInterrupts: " + interrupt;
    }
}
