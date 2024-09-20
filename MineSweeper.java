import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MineSweeper extends JFrame{
    private JButton[][] board;
    private boolean[][] mines;
    private JLabel display;
    private JPanel displayField,buttonField;  
    private  int rows,cols;
    private int[][] bombsNeightbor;
    private boolean[][] visited;
    private int minesLeft,numOfFlag=0;
    ActionListener buttonClicked = new ActionListener() {
       public void actionPerformed(ActionEvent e){
            int currI=0 , currJ=0;
            for(int i =0 ;i<rows;i++){
                for(int j=0;j<cols;j++){
                    if(e.getSource() == board[i][j] ){
                        currI=i;
                        currJ=j;
                        break;
                    }
                }
            }
            if(mines[currI][currJ]){
                board[currI][currJ].setBackground(Color.BLACK);
                displayField.setBackground(Color.RED);
                display.setText("LOSE !!!!!!!!!!!!!!!!");
                for(int i=0 ; i<rows ; i++){
                    for(int j=0 ; j<cols ;j++){
                        if(mines[i][j]){
                            board[i][j].setBackground(Color.BLACK);
                            board[i][j].setText("B!");
                            board[i][j].setForeground(Color.RED);
                            displayField.setBackground(Color.RED);
                        }
                        board[i][j].setEnabled(false);
                        board[i][j].removeMouseListener(mouseClickedButton);
                    }
                }
                endGame(false);
            }else{
                board[currI][currJ].setBackground(Color.YELLOW);
                if(bombsNeightbor[currI][currJ]!=0)board[currI][currJ].setText(bombsNeightbor[currI][currJ]+"");
                if(bombsNeightbor[currI][currJ]==0){
                    openButton(currI, currJ);
                }
                visited[currI][currJ]=true;
                checkEndGame();
            }
       }

    };
    MouseAdapter mouseClickedButton = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            for(int i =0 ;i<rows;i++){
                for(int j=0;j<cols;j++){
                    if(e.getSource() == board[i][j] && e.getButton() == MouseEvent.BUTTON3){
                        // System.out.println("Right at "+i+" " +j);
                        if(!visited[i][j]){
                            if(board[i][j].getText().equals("")){
                                board[i][j].setText("=>");
                                numOfFlag++;
                                board[i][j].setEnabled(false);
                            }else{
                                board[i][j].setText("");
                                numOfFlag--;
                                board[i][j].setEnabled(true);
                                board[i][j].setForeground(Color.BLACK);
                            }
                        }
                         display.setText("Mines Sweeper ( number of mines : "+(minesLeft-numOfFlag)+" )");
                        break;
                    }
                }
            }
        }
    };
    public void endGame(boolean isWin){
        if(isWin){
            display.setText("You win !!!");
            displayField.setBackground(Color.GREEN);   
        }
        for(int i =0;i<rows;i++){
            for(int j=0;j<cols;j++){
                if(mines[i][j]){
                    board[i][j].setBackground(Color.BLACK);
                    board[i][j].setText("B!");
                    board[i][j].setForeground(Color.RED);
                }
            }
        }
        int answer = JOptionPane.showConfirmDialog(null,"Do you want to restart?","Game",JOptionPane.YES_NO_OPTION);
        if (answer==JOptionPane.YES_OPTION) {
            dispose();
            main(null);
        }else{
            System.exit(0);
        }
    }
    public void checkEndGame(){
        for(int i=0;i<rows;i++){
            for(int j=0 ; j<cols ; j++){
                if(mines[i][j]==false && visited[i][j]==false)return; // ถ้าช่องที่ไม่มีระเบิดยังไม่ถูกเปิด เกมยังไม่จบ
            }
        }
        endGame(true);
    }
    public void openButton(int i , int j){
        if(visited[i][j]) return;
        if(i<rows && i>=0 && j<cols && j>=0  && bombsNeightbor[i][j]==0){
            if(board[i][j].getText().equals("=>")){
                minesLeft++;
                board[i][j].setText("");
                board[i][j].setEnabled(true);
            }
            board[i][j].setBackground(Color.YELLOW);
            if(bombsNeightbor[i][j]!=0)board[i][j].setText(bombsNeightbor[i][j]+"");
            visited[i][j] = true;
            try{openButton(i-1, j-1);}catch(Exception err){}
            try{openButton(i, j-1);}catch(Exception err){}
            try{openButton(i+1, j-1);}catch(Exception err){}
            try{openButton(i-1, j);}catch(Exception err){}
            try{openButton(i, j);}catch(Exception err){}
            try{openButton(i+1, j);}catch(Exception err){}
            try{openButton(i-1, j+1);}catch(Exception err){}
            try{openButton(i, j+1);}catch(Exception err){}
            try{openButton(i+1, j+1);}catch(Exception err){}
            display.setText("Mines Sweeper ( number of mines : "+(minesLeft-numOfFlag)+" )");
        }else if(i<rows && i>=0 && j<cols && j>=0){
            if(board[i][j].getText().equals("=>")){
                minesLeft++;
                board[i][j].setText("");
                board[i][j].setEnabled(true);
            }
            board[i][j].setBackground(Color.YELLOW);
            board[i][j].setText(bombsNeightbor[i][j]+"");
            visited[i][j] = true;
        }
    }
    public void checkNeightbor(){
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                if(mines[i][j])continue;
                int numMainsOfNeightbor=0;
                try{if(mines[i-1][j-1])numMainsOfNeightbor++;}catch(Exception err){}
                try{if(mines[i][j-1])numMainsOfNeightbor++;}catch(Exception err){}
                try{if(mines[i+1][j-1])numMainsOfNeightbor++;}catch(Exception err){}
                try{if(mines[i-1][j])numMainsOfNeightbor++;}catch(Exception err){}
                try{if(mines[i+1][j])numMainsOfNeightbor++;}catch(Exception err){}
                try{if(mines[i-1][j+1])numMainsOfNeightbor++;}catch(Exception err){}
                try{if(mines[i][j+1])numMainsOfNeightbor++;}catch(Exception err){}
                try{if(mines[i+1][j+1])numMainsOfNeightbor++;}catch(Exception err){}
                bombsNeightbor[i][j]=numMainsOfNeightbor;
            }
        }
    }
    public MineSweeper(String title,int rows,int cols){
        super(title);  
        UIManager.put("Button.disabledText", Color.RED);
        this.rows=rows;this.cols=cols;
        bombsNeightbor = new int[rows][cols];
        board = new JButton[rows][cols];
        mines = new boolean[rows][cols];
        visited = new boolean[rows][cols];
        setComponent();
        
    }
    public void setComponent(){
        int numMine=0;
        Container con = this.getContentPane();
        displayField = new JPanel();
        display = new JLabel("Mines Sweeper");
        display.setFont(new Font ("TimesRoman", Font.BOLD | Font.ITALIC, 30));
        displayField.add(display);
        displayField.setPreferredSize(new Dimension(100,50));
        displayField.setBackground(Color.GREEN);
        con.add(displayField,BorderLayout.NORTH);
        buttonField = new JPanel(new GridLayout(rows,cols));
        for(int i = 0 ;i<rows;i++){
            for(int j= 0 ; j<cols ;j++){
                board[i][j] = new JButton();
                board[i][j].setFont(new Font ("TimesRoman", Font.BOLD | Font.ITALIC, 20));
                board[i][j].addActionListener(buttonClicked);
                board[i][j].addMouseListener(mouseClickedButton);
                mines[i][j]=(int)(100*Math.random())>90?true:false;
                if(mines[i][j]){
                    numMine++ ;
                    // board[i][j].setBackground(Color.RED);
                }
                buttonField.add(board[i][j]);
            }
        }
        checkNeightbor();
        // System.out.println("Num mine : "+numMine);
        minesLeft = numMine;
        display.setText("Mines Sweeper ( number of mines : "+minesLeft+" )");
        con.add(buttonField);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700,800);
    }
    
    public static void main(String[] args) {
        new MineSweeper("Mine",10, 10);
    }
}