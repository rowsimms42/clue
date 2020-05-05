/*
board_locations = study;



        // change this to a class for the location data so we can have the data stored
        // there and changed all of the location information we need
        // who is here, and other inportand location data needed
    String  [][] grid_location = new String [24][25];

        
    int pixal_with;
    pixal_with = 20;

    int x = e.getX() - 30; // off set by 30
    int y = e.getY() - 13; // off set by 13

    int x_mod = x%20;
    int y_mod = y%20;

    int x_cowordanit = x/pixal_with;
    int y_cowordanit = y/pixal_with;

    if(x_mod >= 2 || x_mod <= 17  || y_mod >= 2 || y_mod <= 17){
        if(x_cowordanit <= 23 || y_cowordanit <= 24)
        {
        return x_cowordanit, y_cowordanit;
        }
        else{
            return bad click try again;
        }
    }
    else{
        return bad click try again;
    }

    public class board_locations_path{
        String name;
        int grid_location [] = new int [2];
        };
        public class board_locations_Study{
            String name = Study;
            int [][] grid_location_s =  {{0,0},{0,1},{0,2},{0,3},{0,4},{0,5},
                                         {1,0},{1,1},{1,2},{1,3},{1,4},{1,5},{1,6},
                                         {2,0},{2,1},{2,2},{2,3},{2,4},{2,5},{2,6},
                                         {3,0},{3,1},{3,2},{3,3},{3,4},{3,5},{3,6}
                                        };

    };
        public class board_locations_library{
            String name = library;
            int [][] grid_location_s =  {      {6,1},{6,2},{6,3},{6,4},{6,5},
                                         {7,0},{7,1},{7,2},{7,3},{7,4},{7,5},{7,6},
                                         {8,0},{8,1},{8,2},{8,3},{8,4},{8,5},{8,6},
                                         {9,0},{9,1},{9,2},{9,3},{9,4},{9,5},{9,6},
                                               {10,1},{10,2},{10,3},{10,4},{10,5},
                                         
                                         
                                        };
        };
        public class board_locations_billiard_room{
            String name = billiard_room;
            int [][] grid_location_s =  {{12,0},{12,1},{12,2},{12,3},{12,4},{12,5},
                                         {13,0},{13,1},{13,2},{13,3},{13,4},{13,5},
                                         {14,0},{14,1},{14,2},{14,3},{14,4},{14,5},
                                         {15,0},{15,1},{15,2},{15,3},{15,4},{15,5},
                                         {16,0},{16,1},{16,2},{16,3},{16,4},{16,5},
                                         {17,0},{17,1},{17,2},{17,3},{17,4},{17,5},
                                        };
        };
        public class board_locations_convervatory{
            String name = convervatory;
            int [][] grid_location_s =  {       {20,1},{20,2},{20,3},{20,4},
                                         {21,0},{21,1},{21,2},{21,2},{21,4},{21,5},
                                         {22,0},{22,1},{22,2},{22,3},{22,4},{22,5},
                                         {23,0},{23,1},{23,2},{23,3},{23,4},{23,5},
                                         {24,0},{24,1},{24,2},{24,3},{24,4},{24,5}
                                                
                                        };
        };
        public class board_locations_hall{
            String name = hall;
            int [][] grid_location_s =  {{0,9},{0,10},{0,11},{0,12},{0,13},{0,14},
                                         {1,9},{1,10},{1,11},{1,12},{1,13},{1,14},
                                         {2,9},{2,10},{2,11},{2,12},{2,13},{2,14},
                                         {3,9},{3,10},{3,11},{3,12},{3,13},{3,14},
                                         {4,9},{4,10},{4,11},{4,12},{4,13},{4,14},
                                         {5,9},{5,10},{5,11},{5,12},{5,13},{5,14},
                                         {6,9},{6,10},{6,11},{6,12},{6,13},{6,14},
                                        };
        };
        public class board_locations_staircase{
            String name = staircase;
            int [][] grid_location_s =  {{8,9},{8,10},{8,11},{8,12},{8,13},
                                        {9,9},{9,10},{9,11},{9,12},{9,13},
                                        {10,9},{10,10},{10,11},{10,12},{10,13},
                                        {11,9},{11,10},{11,11},{11,12},{11,13},
                                        {12,9},{12,10},{12,11},{12,12},{12,13},
                                        {13,9},{13,10},{13,11},{13,12},{13,13},
                                        {14,9},{14,10},{14,11},{14,12},{14,13},
                                        };
        };
        public class board_locations_ballroom{
            String name = ballroom;
            int [][] grid_location_s =  {{17,8},{17,9},{17,10},{17,11},{17,12},{17,13},{17,14},{17,15},
                                         {18,8},{18,9},{18,10},{18,11},{18,12},{18,13},{18,14},{18,15},
                                         {19,8},{19,9},{19,10},{19,11},{19,12},{19,13},{19,14},{19,15},
                                         {20,8},{20,9},{20,10},{20,11},{20,12},{20,13},{20,14},{20,15},
                                         {21,8},{21,9},{21,10},{21,11},{21,12},{21,13},{21,14},{21,15},
                                         {22,8},{22,9},{22,10},{22,11},{22,12},{22,13},{22,14},{22,15},
                                                       {23,10},{23,11},{23,12},{23,13},
                                                       {24,10},{24,11},{24,12},{24,13},
                                        };
        };
        public class board_locations_lounge{
            String name = lounge;
            int [][] grid_location_s =  {      {0,19},{0,20},{0,21},{0,22},{0,23},
                                         {1,18},{1,19},{1,20},{1,21},{1,22},{1,23},
                                         {2,18},{2,19},{2,20},{2,21},{2,22},{2,23},
                                         {3,18},{3,19},{3,20},{3,21},{3,22},{3,23},
                                         {4,18},{4,19},{4,20},{4,21},{4,22},{4,23},
                                         {5,18},{5,19},{5,20},{5,21},{5,22},{5,23},
                                         {6,18},{6,19},{6,20},{6,21},{6,22},{6,23},

                                        };
        };
        public class board_locations_dining_room {
            String name = dining_room;
            int [][] grid_location_s =  { {9,17},{9,18},{9,19},{9,20},{9,21},{9,22},{9,23},
                                          {10,17},{10,18},{10,19},{10,20},{10,21},{10,22},{10,23},
                                          {11,17},{11,18},{11,19},{11,20},{11,21},{11,22},{11,23},
                                          {12,17},{12,18},{12,19},{12,20},{12,21},{12,22},{12,23},
                                          {13,17},{13,18},{13,19},{13,20},{13,21},{13,22},{13,23},
                                          {14,17},{14,18},{14,19},{14,20},{14,21},{14,22},{14,23},
                                                                  {15,20},{15,21},{15,22},{15,23},
                                        };
        };
        public class board_locations_kitchen{
            String name = kitchen;
            int [][] grid_location_s =  {{18,18},{18,19},{18,20},{18,21},{18,22},{18,23},
                                         {19,18},{19,19},{19,20},{19,21},{19,22},{19,23},
                                         {20,18},{20,19},{20,20},{20,21},{20,22},{20,23},
                                         {21,18},{21,19},{21,20},{21,21},{21,22},{21,23},
                                         {22,18},{22,19},{22,20},{22,21},{22,22},{22,23},
                                         {23,18},{23,19},{23,20},{23,21},{23,22},{23,23},
                                        };
        };
        //class for all of the tiles that are not rooms
public class board_locations_tiles extends board_locations_tile{
    //build a class array that has the information on the tiles

};
        // base class that we can add to at tiles class 
        public class board_locations_tile{ 
            //is this space occupied
            boolean occupied = false;
            //to indicate the player based off there player number from 1-6
            int player = 0;
        };

        List<int[]> intArrays=new ArrayList<>();
        int anExample[]={1,2,3};

        for(int[] anIntArray:intArrays) {
            //iterate the retrieved array an print the individual elements
            for (int aNumber : anIntArray) {
                System.out.println("Arraylist contains:" + aNumber);
            }


        // player class that has a grid location saved and any otherinformation needed
        // 

    }
*/