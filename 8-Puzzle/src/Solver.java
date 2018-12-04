import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;


public class Solver {
    private Node solved;
    private Stack<Board> solution;
    private Board initial;

    private class Node implements Comparable<Node> {
        private final Board board;
        private final int moves;
        private final Node prev_node;
        private final int priority;

        public Node(Board board, int moves, Node prev_node) {
            this.board = board;
            this.moves = moves;
            this.prev_node = prev_node;
            priority = moves + board.manhattan();
        }

        @Override
        public int compareTo(Node o) {
            return this.priority - o.priority;
        }

        public int getMoves() {
            return moves;
        }
    }

    public Solver(Board initial) { this.initial = initial; }

    public boolean isSolvable() {
        MinPQ<Node> pq = new MinPQ<>();
        MinPQ<Node> pqtwin = new MinPQ<>();

        Stack<Board> stack = new Stack<>();
        Stack<Board> twinStack = new Stack<>();

        stack.push(initial);
        Board twin = initial.twin();
        twinStack.push(twin);

        pq.insert(new Node(initial, 0, null));
        pqtwin.insert(new Node(twin, 0, null));

        Node prev = pq.delMin();
        Node prevTwin = pqtwin.delMin();

        while (!prev.board.isGoal() && !prevTwin.board.isGoal()) {
            for (Board neighbour : prev.board.neighbors()) {
                if(prev.prev_node != null) {
                    if (neighbour.equals(prev.prev_node.board))
                        continue;
                }
                    pq.insert(new Node(neighbour, prev.getMoves()+1,prev));
            }

            for (Board neighbour : prevTwin.board.neighbors()) {
                if(prevTwin.prev_node != null) {
                    if (neighbour.equals(prevTwin.prev_node.board))
                        continue;
                }
                    pqtwin.insert(new Node(neighbour, prevTwin.getMoves()+1,prevTwin));
            }

            prev = pq.delMin();
            prevTwin = pqtwin.delMin();

            stack.push(prev.board);
            boolean y = prev.board.isGoal();
            twinStack.push(prevTwin.board);


        }

        if(prev.board.isGoal()) {
            solved = prev;
            solution = stack;
            return true;
        }

        return false;
    }

    public int moves() { return solved.moves; }

    public Iterable<Board> solution() { return this.solution; }


    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);
        solver.isSolvable();


            StdOut.println("Minimum number of moves = " + solver.moves());

    }




}
