//import mpi.MPI;

import java.util.Scanner;

import mpi.*;

public class sum {

    public static void main(String args[]) throws Exception {

        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        System.out.println("Size is" + size);

        // Make arrays

        int N = 20;

        // per_array_elements * size = N

        int[] full_array =  new int[N];
        int[] local_array = new int[4];
        int[] all_sums = new int[5];

        if (rank == 0) {

            for (int i = 0; i < N; i++) {
                full_array[i] = i+1;
            }
        }

        // Scatter the processes

        MPI.COMM_WORLD.Scatter(
            full_array,
            0,
            4,
            MPI.INT,
            local_array,
            0,
            4,
            MPI.INT,
            0
        );

        // Calculate the local sum

        int local_sum = 0;

        for (int i = 0; i < local_array.length; i++) {
            local_sum = local_sum + local_array[i];
        }

        System.out.println(
            "Intermediate sum at process " + rank + " is " + local_sum);

        // Gather local sums
  

        MPI.COMM_WORLD.Gather(
            new int[]{local_sum},
            0,
            1,
            MPI.INT,
            all_sums,
            0,
            1,
            MPI.INT,
            0
        );

        // Print intermediate sums and overall sum

        if (rank == 0) {

            int final_sum = 0;

            for (int i = 0; i < size; i++) {
                final_sum = final_sum + all_sums[i];
    
            }
            System.out.println("Total sum is " + final_sum);
        }

        MPI.Finalize();

    }

}