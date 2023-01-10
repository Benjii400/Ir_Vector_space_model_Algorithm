

import java.util.Scanner;
import java.util.Arrays;
import java.lang.Math;

public class irVectorspaceAlorithem {

    //This class counts the number of uniqe terms in all documents
    static  int countNoRep (String[] a){
        int count=0;
        boolean check = true;
        for(int i=0; i < a.length; i++){
            for(int j=i+1; j < a.length; j++){

                if(a[i].equals(a[j])){

                    for(int k=i-1; k>=0; k--){
                        if(a[k].equals(a[i])){

                            check=false;
                            break;
                        }
                    }
                    if(check ) count++;
                    else check = true;
                }
            }
        }
        return a.length-count;
    }




    public static void main(String[] args) {

        Scanner x = new Scanner(System.in);
        Scanner y = new Scanner(System.in);

        //To accept the number of documents
        System.out.println("\n\n\n***********************************      Welcome to Vector space IR system        **************************** ");
        System.out.print("\nEnter number of documents: ");
        int num = y.nextInt();
        String[] doc = new String[num];

        //To accepet the documents by iteraton
        String[][] word = new String[num + 1][];
        int countTerm = 0;
        for (int i = 0; i < num; i++) {
            System.out.print("Enter document " + (i + 1) + ": ");
            doc[i] = x.nextLine();
            word[i] = doc[i].split(" ");//TO tokenize the document
            for (int j = 0; j < word[i].length; j++) {
                word[i][j] = word[i][j].toLowerCase();
            }
        }


        String query;
        System.out.print("Enter the query: ");
        query = x.nextLine();
        word[num] = query.split(" ");
        for (int k = 0; k < word[num].length; k++)
            word[num][k] = word[num][k].toLowerCase();

        /*
        for (int i = 0; i < word.length; i++) {
            for (int j = 0; j < word[i].length; j++) {
                System.out.print(word[i][j] + " ");
            }
            System.out.println(" ");
        }
         */

        //This loop counts the total number of terms in all documents
        for (int i = 0; i < word.length; i++) {
            for (int j = 0; j < word[i].length; j++) {
                countTerm++;
            }
        }


        //To assign the total terms to the array terms[]
        String[] terms = new String[countTerm];
        int o = 0;

        for (int i = 0; i < word.length; i++) {
            for (int j = 0; j < word[i].length; j++) {
                terms[o] = word[i][j];
                terms[o] = terms[o].toLowerCase();
                o++;
            }
        }


        //This will call the metheod countNoRep() and returns the number of the uniqe terms
        int p = countNoRep(terms);


        //Assign the array termNorep[] with one term
        String[] termNorep = new String[p];
        int j = 0;
        for (int i = 0; i < p; i++) {
            termNorep[i] = terms[0];
            j++;
        }
        j = 1;


        //Compare each terms of the uniqe array termNorep[]
        //with the array holding all the terms terms[] and assign only the uniqu ones
        for (int i = 0; i < terms.length; i++) {
            a:
            for (int k = 0; k < termNorep.length; k++) {
                if (termNorep[k].equals(terms[i]) && j < p) break a;
                else if (!termNorep[k].equals(terms[i]) && j < p) {
                    for (int l = 0; l < p; l++) {
                        if (termNorep[l].equals(terms[i]) && j < p) break a;
                    }

                    termNorep[j] = terms[i];
                    j++;
                }
            }
        }

/*

        for (int i = 0; i < termNorep.length; i++) {
            System.out.println(termNorep[i] + " ");
        }

 */

        //Calculate and assign the term frequency of each term in each document
        int[][] termFrq = new int[num + 1][p];
        int count = 0;
        int i = 0;
        while (i < termNorep.length) {
            for (int k = 0; k < word.length; k++) {
                for (int l = 0; l < word[k].length; l++) {
                    if (termNorep[i].equals(word[k][l])) {
                        count++;
                    }
                }
                termFrq[k][i] = count;
                count = 0;
            }
            i++;
        }

/*
        //To display the term frequency
        for (int k = 0; k < word.length; k++) {
            for (int l = 0; l < p; l++) {
                System.out.print(termFrq[k][l] + " ");
            }
            System.out.println(" ");
        }
*/

        //Calculate and assign the document frequency of each term
        int[] docFrq = new int[p];
        count = 0;
        i = 0;
        while (i < termNorep.length) {
            for (int k = 0; k < word.length - 1; k++) {
                for (int l = 0; l < word[k].length; l++) {
                    if (termNorep[i].equals(word[k][l])) {
                        count++;
                        break;
                    }
                }
            }
            docFrq[i] = count;
            i++;
            count = 0;
        }


        //To display the doc frequency
/*
        System.out.println(" ");
        System.out.println(" ");
        for (int l = 0; l < p; l++) {
            System.out.print(docFrq[l] + " ");
        }
*/

        //Calculate and assign the document / document frequency
        float numOfDoc = (int) num;
        float dOverDfi[] = new float[p];
        double Idf[] = new double[p];
        for (int k = 0; k < p; k++) {
            dOverDfi[k] = numOfDoc / docFrq[k];
            Idf[k] = Math.log(dOverDfi[k]) / Math.log(2);
        }


        //Display  the document / document frequency
/*        System.out.println(" ");
        System.out.println(" ");
        for (int l = 0; l < p; l++) {
            System.out.print(dOverDfi[l] + " ");
        }


        System.out.println(" ");
        System.out.println(" ");
        for (int l = 0; l < p; l++) {
            System.out.print(Idf[l] + " ");
        }
*/

        //Calculate and assign the weight
        double weight[][] = new double[num + 1][p];
        for (int k = 0; k < word.length; k++) {
            for (int l = 0; l < p; l++) {
                weight[k][l] = Idf[l] * termFrq[k][l];
            }
        }


        //Display  the weight
/*
        System.out.println(" ");
        System.out.println(" ");
        for (int k = 0; k < num + 1; k++) {
            for (int l = 0; l < p; l++) {
                System.out.print(weight[k][l] + " ");
            }
            System.out.println(" ");
        }
*/

        //Calculate and assign the magnitude of the weight
        double magnitude[] = new double[num + 1];
        double temp = 0;
        for (int k = 0; k < num + 1; k++) {
            for (int l = 0; l < p; l++) {
                temp = temp + Math.pow(weight[k][l], 2);
            }
            magnitude[k] = Math.sqrt(temp);
            temp = 0;
        }


        //Display  the magnitude of the weight
        /*
        System.out.println(" ");
        System.out.println(" ");
        for (int l = 0; l < num + 1; l++) {
            System.out.print(magnitude[l] + " ");
        }
          */

        //Calculate and assign the queryTimesdoc
        temp = 0;
        i = 0;
        double queryTimedoc[] = new double[num];
        for (int k = 0; k < word.length - 1; k++) {
            for (int l = 0; l < p; l++) {
                temp = temp + (weight[num][l] * weight[k][l]);
            }
            queryTimedoc[i] = temp;
            i++;
            temp = 0;
        }


        //To display the queryTimesdoc
  /*      System.out.println(" ");
        System.out.println(" ");
        for (int l = 0; l < num; l++) {
            System.out.print(queryTimedoc[l] + " ");
        }
   */

        //Calculate and assign the Similarity of documents with query
        double sim[][] = new double[num][2];
        for (int l = 0; l < num; l++) {
            for (int k = 0; k < 2; k++) {
                if (k == 0) sim[l][k] = ((queryTimedoc[l] / (magnitude[num] * magnitude[l])));
                else if (k == 1) sim[l][k] = l;
            }
        }
//display sim
/*
        System.out.println("\n");
        for (int l = 0; l < num; l++) {
            for (int k = 0; k < 2; k++) {
                System.out.print(sim[l][k] + " ");
            }
            System.out.print("\n");
        }*/

        double sim2[][] = new double[num][2];
        for (int l = 0; l < num; l++) {
            for (int k = 0; k < 2; k++) {
                if (k == 0) sim2[l][k] = ((queryTimedoc[l] / (magnitude[num] * magnitude[l])));
                else if (k == 1) sim2[l][k] = l ;
            }
        }

        double hold[][] = new double[2][num];
        hold[0] = sim2[0];
        double temp1, temp2 = 0;
        for (int l = 0; l < num; l++) {
            for (int k = 0; k < num - 1; k++) {
                temp1 =(sim2[k][0]);
                temp2 =(sim2[k + 1][0]);
                if (temp1 < temp2) {
                    hold[0] = sim2[k + 1];
                    sim2[k + 1] = sim2[k];
                    sim2[k] = hold[0];
                }
            }
        }


        //To display the sim2

        /*System.out.println("\n");
        for (int l = 0; l < num; l++) {
            for (int k = 0; k < 2; k++) {
                System.out.print(sim2[l][k] + " ");
            }
            System.out.print("\n");
        }
*/
        while (true) {


            System.out.println("\nChoose from the following");
            int t=1;
            for (int l = 0; l < num; l++) {
                for (int k = 0; k < num; k++) {
                    if (sim2[l][t] == sim[k][t] )
                        System.out.println((k + 1) + " " + word[k][0] + " " + word[k][1] + " " + word[k][2] + " " + word[k][3] + " .......");
                }

            }
            System.out.println("If you want to exit press 0\n");
            int cho = x.nextInt();
            if (cho == 0) System.exit(0);
            for (int l = 0; l < word[cho - 1].length; l++) {
                System.out.print(word[cho - 1][l] + " ");
            }
            System.out.print("\n");
        }

    }
}

