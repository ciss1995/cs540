package aihw6;

import java.util.*;

class State {
    char[] board;

    public State(char[] arr) {
        this.board = Arrays.copyOf(arr, arr.length);
    }

    public int getScore() {
        // TO DO: return game theoretic value of the board
    	int score = 0;
    	int count1 = 0;
    	int count2 =0;
    	for (int i = 0; i < board.length; i++){
    		if (board[i] == '1') count1++;
    		if (board[i] == '2') count2++;
    	}
    	if (count1 > count2) score = 1;
    	else if(count1 < count2) score = -1;
        return score;
    }
    
    public boolean isTerminal() {	
        // TO DO: determine if the board is a terminal node or not and return boolean
    	boolean is_terminal = false;
    	for(int i = 0; i < board.length; i++){
    		if (board[i] != 0){
    			is_terminal = true;
    		}
    	}
    	if (this.getSuccessors('1') != null) is_terminal = false; 
    	if (this.getSuccessors('2') != null) is_terminal = false;
        return is_terminal;
    }
    
    public State[] getSuccessors(char player) {
        // TO DO: get all successors and return them in proper order   	
        ArrayList<State> list = new ArrayList<>();
        boolean modify = false;
        char curr = player;
        char other = '0';
        if (curr == '1')other = '2';
        else if (curr == '2') other = '1';
        
        for(int i=0; i<board.length; i++){
            int c = i%4 + 1;
            int r = i/4 + 1;
            //reset
            char[] arr = Arrays.copyOf(this.board, this.board.length);
            //down from top left
            if(board[i] == '0' && r <= 2 && board[i+4] == other && board[i+8] == curr && board[i] == '0'){
                arr[i] = curr;
                arr[i+4] = curr;
                modify = true;
            }
            else if(board[i] == '0' && r <= 2 && r == 1){
                if(board[i+4] == other && board[i+8] == other && board[i+12] == curr && board[i] == '0'){
                    arr[i] = curr;
                    arr[i+4] = curr;
                    arr[i+8] = curr;
                    modify = true;
                }
            }
            //up from top left
            if(board[i] == '0' && r <= 2 && c<=2 && board[i+1] == other && board[i+2] == curr){
                arr[i] = curr;
                arr[i+1] = curr;
                modify = true;
            }
            else if(board[i] == '0' && r <= 2 && c<=2 && c == 1){
                if(board[i+1] == other && board[i+2] == other && board[i+3] == curr){
                    arr[i] = curr;
                    arr[i+1] = curr;
                    arr[i+2] = curr;
                    modify = true;
                }
            }
            //diagonal from top left
            if(board[i] == '0' && r <= 2 && c<=2 && board[i+5] == other && board[i+10] == curr){
                arr[i] = curr;
                arr[i+5] = curr;
                modify = true;
            }
            else if(board[i] == '0' && r <= 2 && c<=2 && r == 1 && c == 1){
                if(board[i+5] == other && board[i+10] == other && board[i+15] == curr){
                    arr[i] = curr;
                    arr[i+5] = curr;
                    arr[i+10] = curr;
                    modify = true;
                }
            }
            //Left From top right
            if(board[i] == '0' && r <= 2 && c > 2 && board[i-1] == other && board[i-2] == curr){
                arr[i] = curr;
                arr[i-1] = curr;
                modify = true;
            }
            else if(board[i] == '0' && r <= 2 && c == 4){
                if(board[i-1] == other && board[i-2] == other && board[i-3] == curr){
                    arr[i] = curr;
                    arr[i-1] = curr;
                    arr[i-2] = curr;
                    modify = true;
                }
            }
            //Diagonal From top right
            if(board[i] == '0' && r <= 2 && c > 2 && board[i+3] == other && board[i+6] == curr){
                arr[i] = curr;
                arr[i+3] = curr;
                modify = true;
            }
            else if(board[i] == '0' && r == 1 && c == 4){
                if(board[i+3] == other && board[i+6] == other && board[i+9] == curr){
                    arr[i] = curr;
                    arr[i+3] = curr;
                    arr[i+6] = curr;
                    modify = true;
                }
            }
            //Up From down left
            if(r > 2 && c <= 2 && board[i] == '0' && board[i-4] == other && board[i-8] == curr){
                arr[i] = curr;
                arr[i-4] = curr;
                modify = true;
            }
            else if(r == 4  && c <= 2 && board[i] == '0'){
                if(board[i-4] == other && board[i-8] == other && board[i-12] == curr){
                    arr[i] = curr;
                    arr[i-4] = curr;
                    arr[i-8] = curr;
                    modify = true;
                }
            }
            //Right From down left
            if(r > 2 && c <= 2 && board[i] == '0' && board[i+1] == other && board[i+2] == curr){
                arr[i] = curr;
                arr[i+1] = curr;
                modify = true;
            }
            else if(r > 2 && c == 1 && board[i] == '0'){
                if(board[i+1] == other && board[i+2] == other && board[i+3] == curr){
                    arr[i] = curr;
                    arr[i+1] = curr;
                    arr[i+2] = curr;
                    modify = true;
                }
            }
            
            //Diagonal From down left
            if(r > 2 && c <= 2 && board[i] == '0' && board[i-3] == other && board[i-6] == curr){
                arr[i] = curr;
                arr[i-3] = curr;
                modify = true;
            }
            else if(board[i] == '0' && r == 4 && c == 1){
                if(board[i-3] == other && board[i-6] == other && board[i-9] == curr){
                    arr[i] = curr;
                    arr[i-3] = curr;
                    arr[i-6] = curr;
                    modify = true;
                }
            }
            //Up From down right
            if(board[i] == '0' && r > 2 && c > 2 && board[i-4] == other && board[i-8] == curr){
                arr[i] = curr;
                arr[i-4] = curr;
                modify = true;
            }
            else if(board[i] == '0'&& r == 4 && c > 2){
                if(board[i-4] == other && board[i-8] == other && board[i-12] == curr){
                    arr[i] = curr;
                    arr[i-4] = curr;
                    arr[i-8] = curr;
                    modify = true;
                }
            }
            //left From down right
            if(board[i] == '0' && r > 2 && c > 2 && board[i-1] == other && board[i-2] == curr){
                arr[i] = curr;
                arr[i-1] = curr;
                modify = true;
            }
            else if(board[i] == '0' && r > 2 && c == 4){
                if(board[i-1] == other && board[i-2] == other && board[i-3] == curr){
                    arr[i] = curr;
                    arr[i-1] = curr;
                    arr[i-2] = curr;
                    modify = true;
                }
            }
            //Diagonal From down right
            if(board[i] == '0' && r > 2 && c > 2 && board[i-5] == other && board[i-10] == curr){
                arr[i] = curr;
                arr[i-5] = curr;
                modify = true;
            }
            else if(board[i] == '0' && r == 4 && c == 4){
                if(board[i-5] == other && board[i-10] == other && board[i-15] == curr){
                    arr[i] = curr;
                    arr[i-5] = curr;
                    arr[i-10] = curr;
                    modify = true;
                }
            }
            State st = new State(arr);
            if(!st.equals(this)){
                list.add(st);
            }
        }
        //Append to array
        State[] successors = new State[list.size()];
        for(int j = 0; j < list.size(); j++){
            successors[j] = list.get(j);
        }
        
        if (modify == false) successors = null;
        return successors;
    }
    
    public void printState(int option, char player) {
        // TO DO: print a State based on option (flag)
    	State [] s = this.getSuccessors(player);
    	Minimax mm = new Minimax();
    	if (option < 1) System.out.println("Flag Error");
    	//Flag 100
    	if (option == 1 && s != null){
    		for (int i = 0; i < s.length; i++){
    			if (s[i] != null) System.out.println(s[i].getBoard());
    		}	
    	}
    	//Flag 200
    	else if (option == 2){
    		if (this.isTerminal() != true) System.out.println("non-terminal");
    		else System.out.println(this.getScore());
    	}
    	//Flag 300
    	else if (option == 3){
    		int value = mm.run(this, player);
    		System.out.println(value);
    		System.out.println(mm.counter);
    	}
    	//Flag 400
    	else if (option == 4){
    		mm.run(this, player);
    		if (mm.s != null) System.out.println(mm.s.getBoard());
    	}
    	//Flag >500
    	else if (option >= 5){
    		//Flag 500
    		if (option == 5) {
    			int value = mm.run_with_pruning(this, player);
        		System.out.println(value);
        		System.out.println(mm.counter);
    		}
    		//Flag 600
    		else if (option == 6){
    			mm.run_with_pruning(this, player);
    			if (mm.s != null) System.out.println(mm.s.getBoard());
    		}
    	}
    }

    public String getBoard() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            builder.append(this.board[i]);
        }
        return builder.toString().trim();
    }

    public boolean equals(State src) {
        for (int i = 0; i < 16; i++) {
            if (this.board[i] != src.board[i])
                return false;
        }
        return true;
    }
}

class Minimax {
	static int counter;
	static State s;
	static int length;
	
	private static int max_value(State curr_state) {
        // TO DO: implement Max-Value of the Minimax algorithm
		counter++;
		length++;
		int v = 0;
		//if ( s is a terminal state ) then return ( terminal value of s ) 
		if (curr_state.isTerminal() == true) {
			length--;
			return curr_state.getScore();
		}
		//α := –infinity
		int alpha = Integer.MIN_VALUE;
		//generate successors
		State[] sucessor1 = curr_state.getSuccessors('1');
		// check if successors for this player
		if (sucessor1 != null){
			// For each sucessor α := max( α , Min-value(s’))
			for (int i = 0; i < sucessor1.length; i++){
				if (sucessor1[i] != null) {
					v = min_value(sucessor1[i]);
					if (alpha < v) {
						alpha = v;
						if (length == 1) {
							s = sucessor1[i];
						}
					}
				}
			}
		} else {
			//Max player, play again
			length--;
			alpha = min_value(curr_state);
			return alpha;
		}
		length--;
		return alpha;
	}
	private static int min_value(State curr_state) {	
		// TO DO: implement Min-Value of the Minimax algorithm     1111111111111200    0111121111111110
		counter++;
		length++;
		int v = 0;
		//if ( s is a terminal state ) then return ( terminal value of s ) 
		if (curr_state.isTerminal() == true) {
			length--;
			return curr_state.getScore();
		}
		//α := +infinity
		int alpha = Integer.MAX_VALUE;
		//generate successors
		State[] sucessor2 = curr_state.getSuccessors('2');
		// check if successors for this player
		if (sucessor2 != null){
			// For each sucessor α := min( β , Max-value(s’)) 
			for (int i = 0; i < sucessor2.length; i++){
				if (sucessor2[i] != null){
					v = max_value(sucessor2[i]);
					if (alpha > v) {
						alpha = v;
						if (length == 1) {
							s = sucessor2[i];
						}
					}
				}
			}
		} else {
			//Min player play again
			length--;
			alpha = max_value(curr_state);
			return alpha;
		}
		length--;
		return alpha;		
	}
	
	private static int max_value_with_pruning(State curr_state, int alpha, int beta) {    
        // TO DO: implement Max-Value of the alpha-beta pruning algorithm
		counter++;
		length++;
		int v = 0;
		//if ( s is a terminal state ) then return ( terminal value of s ) 
		if (curr_state.isTerminal() == true) {
			length--;
			return curr_state.getScore();
		}
		//generate successors
		State[] sucessor1 = curr_state.getSuccessors('1');
		// check if successors for this player
		if (sucessor1 != null){
			// For each sucessor α := max( α , Min-value(s’,α,β))  
			for (int i = 0; i < sucessor1.length; i++){
				if (sucessor1[i] != null) {
					v = min_value_with_pruning(sucessor1[i], alpha, beta);
					if (alpha < v) {
						alpha = v;
						if (length == 1){
							s = sucessor1[i];
						}
					}
					// if ( α ≥ β ) then return β
					if (alpha >= beta) {
						length--;
						return beta;
					}
				}
			}
		} else {
			//Max play again
			length--;
			alpha = min_value_with_pruning(curr_state, alpha, beta);
			return alpha;
		}
		length--;
		return alpha;
	}
	
	private static int min_value_with_pruning(State curr_state, int alpha, int beta) {   
        // TO DO: implement Min-Value of the alpha-beta pruning algorithm
		counter++;
		length++;
		int v = 0;
		//if ( s is a terminal state ) then return ( terminal value of s ) 
		if (curr_state.isTerminal() == true) {
			length--;
			return curr_state.getScore();
		}
		//generate successors
		State[] sucessor2 = curr_state.getSuccessors('2');
		// check if successors for this player
		if (sucessor2 != null){
			// For each sucessor β := min( β , Max-value(s’,α,β))
			for (int i = 0; i < sucessor2.length; i++){
				if (sucessor2[i] != null) {
					v = max_value_with_pruning(sucessor2[i], alpha, beta);
					if (beta > v) {
						if (length == 1) s = sucessor2[i];
						beta = v;
					}
					// if (α ≥ β ) then return α
					if (alpha >= beta) {
						length--;
						return alpha;
					}
				}
			}
		} else {
			//Min play again
			length--;
			beta = max_value_with_pruning(curr_state, alpha, beta);
			return beta;
		}
		length--;
		return beta;
	}
	
	public static int run(State curr_state, char player) {
        // TO DO: run the Minimax algorithm and return the game theoretic value
		int v = 0;
		if (player == '1') {
			v = max_value(curr_state);
		} else {
			v = min_value(curr_state);
		}
		return v;
	}
	
	public static int run_with_pruning(State curr_state, char player) {   
        // TO DO: run the alpha-beta pruning algorithm and return the game theoretic value
		int v = 0;
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		if (player == '1') {
			v = max_value_with_pruning(curr_state, alpha, beta);
		} else {
			v = min_value_with_pruning(curr_state, alpha, beta);
		}
		return v;		
	}
}

public class Reversi {
    public static void main(String args[]) {
        if (args.length != 3) {
            System.out.println("Invalid Number of Input Arguments");
            return;
        }
        int flag = Integer.valueOf(args[0]);
        char[] board = new char[16];
        for (int i = 0; i < 16; i++) {
            board[i] = args[2].charAt(i);
        }
        int option = flag / 100;
        char player = args[1].charAt(0);
        if ((player != '1' && player != '2') || args[1].length() != 1) {
            System.out.println("Invalid Player Input");
            return;
        }
        State init = new State(board);
        init.printState(option, player);
    }
}
