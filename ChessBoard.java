package chess;
	
	import java.awt.*;
	import java.awt.event.*;
	import java.awt.image.BufferedImage;
	import javax.swing.*;
	import javax.swing.border.*;
	import java.util.ArrayList;

	// Enumeration for different types of chess pieces.
	enum PieceType {king, queen, bishop, knight, rook, pawn, none}

	// Enumeration for player colors.
	enum PlayerColor {black, white, none}

	// ChessBoard class represents the main structure of the chess game.
	public class ChessBoard {
		private final JPanel gui = new JPanel(new BorderLayout(3, 3));
		private JPanel chessBoard;
		private JButton[][] chessBoardSquares = new JButton[8][8];
		private Piece[][] chessBoardStatus = new Piece[8][8];
		private ImageIcon[] pieceImage_b = new ImageIcon[7];
		private ImageIcon[] pieceImage_w = new ImageIcon[7];
		private JLabel message = new JLabel("Click to Start");

		// Constructor of the ChessBoard class.
		ChessBoard() {
			initPieceImages(); // Initializes images for each chess piece.
			initBoardStatus(); // Initializes the status of each square on the board.
			initializeGui(); // Sets up the GUI layout and components.
		}
		
		// Initializes the board status by placing empty pieces on each square.
		public final void initBoardStatus() {
			for(int i=0;i<8;i++) {
				for(int j=0;j<8;j++) chessBoardStatus[j][i] = new Piece();
			}
		}
		
		// Initializes and scales the images for each type of chess piece.
		public final void initPieceImages() {
			// Black piece images
			pieceImage_b[0] = new ImageIcon(new ImageIcon("./img/king_b.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
			pieceImage_b[1] = new ImageIcon(new ImageIcon("./img/queen_b.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
			pieceImage_b[2] = new ImageIcon(new ImageIcon("./img/bishop_b.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
			pieceImage_b[3] = new ImageIcon(new ImageIcon("./img/knight_b.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
			pieceImage_b[4] = new ImageIcon(new ImageIcon("./img/rook_b.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
			pieceImage_b[5] = new ImageIcon(new ImageIcon("./img/pawn_b.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
			pieceImage_b[6] = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
			
			// White piece images
			pieceImage_w[0] = new ImageIcon(new ImageIcon("./img/king_w.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
			pieceImage_w[1] = new ImageIcon(new ImageIcon("./img/queen_w.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
			pieceImage_w[2] = new ImageIcon(new ImageIcon("./img/bishop_w.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
			pieceImage_w[3] = new ImageIcon(new ImageIcon("./img/knight_w.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
			pieceImage_w[4] = new ImageIcon(new ImageIcon("./img/rook_w.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
			pieceImage_w[5] = new ImageIcon(new ImageIcon("./img/pawn_w.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
			pieceImage_w[6] = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
		}
		
		// Retrieves the image icon for a given chess piece.
		public ImageIcon getImageIcon(Piece piece) {
			if(piece.color.equals(PlayerColor.black)) {
				if(piece.type.equals(PieceType.king)) return pieceImage_b[0];
				else if(piece.type.equals(PieceType.queen)) return pieceImage_b[1];
				else if(piece.type.equals(PieceType.bishop)) return pieceImage_b[2];
				else if(piece.type.equals(PieceType.knight)) return pieceImage_b[3];
				else if(piece.type.equals(PieceType.rook)) return pieceImage_b[4];
				else if(piece.type.equals(PieceType.pawn)) return pieceImage_b[5];
				else return pieceImage_b[6];
			}
			else if(piece.color.equals(PlayerColor.white)) {
				if(piece.type.equals(PieceType.king)) return pieceImage_w[0];
				else if(piece.type.equals(PieceType.queen)) return pieceImage_w[1];
				else if(piece.type.equals(PieceType.bishop)) return pieceImage_w[2];
				else if(piece.type.equals(PieceType.knight)) return pieceImage_w[3];
				else if(piece.type.equals(PieceType.rook)) return pieceImage_w[4];
				else if(piece.type.equals(PieceType.pawn)) return pieceImage_w[5];
				else return pieceImage_w[6];
			}
			else return pieceImage_w[6];
		}

		// Sets up the main GUI for the chess game.
		public final void initializeGui() {
			gui.setBorder(new EmptyBorder(5, 5, 5, 5));
		    JToolBar tools = new JToolBar();
		    tools.setFloatable(false);
		    gui.add(tools, BorderLayout.PAGE_START);
		    JButton startButton = new JButton("Start");
			startButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					initiateBoard();
				}
			});
		    
		    tools.add(startButton);
		    tools.addSeparator();
		    tools.add(message);

		    chessBoard = new JPanel(new GridLayout(0, 8));
		    chessBoard.setBorder(new LineBorder(Color.BLACK));
		    gui.add(chessBoard);
		    ImageIcon defaultIcon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
		    Insets buttonMargin = new Insets(0,0,0,0);
		    for(int i=0; i<chessBoardSquares.length; i++) {
		        for (int j = 0; j < chessBoardSquares[i].length; j++) {
		        	JButton b = new JButton();
		        	b.addActionListener(new ButtonListener(i, j));
		            b.setMargin(buttonMargin);
		            b.setIcon(defaultIcon);
		            if((j % 2 == 1 && i % 2 == 1)|| (j % 2 == 0 && i % 2 == 0)) b.setBackground(Color.WHITE);
		            else b.setBackground(Color.gray);
		            b.setOpaque(true);
		            b.setBorderPainted(false);
		            chessBoardSquares[j][i] = b;
		        }
		    }

		    for (int i=0; i < 8; i++) {
		        for (int j=0; j < 8; j++) {
					chessBoard.add(chessBoardSquares[j][i]);
				}
		    }
		}

		// Returns the main GUI component.
		public final JComponent getGui() {
		    return gui;
		}
		
		// Main method to run the chess game application.
		public static void main(String[] args) {
		    Runnable r = new Runnable() {
		        @Override
		        public void run() {
		        	ChessBoard cb = new ChessBoard();
	                JFrame f = new JFrame("Chess");
	                f.add(cb.getGui());
	                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	                f.setLocationByPlatform(true);
	                f.setResizable(false);
	                f.pack();
	                f.setMinimumSize(f.getSize());
	                f.setVisible(true);
	            }
	        };
	        SwingUtilities.invokeLater(r);
		}
			
		// Inner class representing a chess piece.
		class Piece {
			PlayerColor color;
			PieceType type;
			
			// Default constructor sets piece to no color and type.
			Piece() {
				color = PlayerColor.none;
				type = PieceType.none;
			}

			// Constructor to set a specific color and type to the piece.
			Piece(PlayerColor color, PieceType type) {
				this.color = color;
				this.type = type;
			}
		}
		
		// Sets the icon for a specific square on the chess board.
		public void setIcon(int x, int y, Piece piece) {
			chessBoardSquares[y][x].setIcon(getImageIcon(piece));
			chessBoardStatus[y][x] = piece;
		}
		
		// Gets the icon (piece) of a specific square on the chess board.
		public Piece getIcon(int x, int y) {
			return chessBoardStatus[y][x];
		}
		
		// Highlights a specific square on the chess board.
		public void markPosition(int x, int y) {
			chessBoardSquares[y][x].setBackground(Color.yellow);
		}
		
		// Removes the highlight from a specific square on the chess board.
		public void unmarkPosition(int x, int y) {
			if((y % 2 == 1 && x % 2 == 1)|| (y % 2 == 0 && x % 2 == 0)) chessBoardSquares[y][x].setBackground(Color.WHITE);
			else chessBoardSquares[y][x].setBackground(Color.gray);
		}
		
		// Sets the status message on the GUI.
		public void setStatus(String inpt) {
			message.setText(inpt);
		}
		
		// Initializes the chess board with default piece positions.
		public void initiateBoard() {
			for(int i=0;i<8;i++) {
				for(int j=0;j<8;j++) setIcon(i, j, new Piece());
			}
			setIcon(0, 0, new Piece(PlayerColor.black, PieceType.rook));
			setIcon(0, 1, new Piece(PlayerColor.black, PieceType.knight));
			setIcon(0, 2, new Piece(PlayerColor.black, PieceType.bishop));
			setIcon(0, 3, new Piece(PlayerColor.black, PieceType.queen));
			setIcon(0, 4, new Piece(PlayerColor.black, PieceType.king));
			setIcon(0, 5, new Piece(PlayerColor.black, PieceType.bishop));
			setIcon(0, 6, new Piece(PlayerColor.black, PieceType.knight));
			setIcon(0, 7, new Piece(PlayerColor.black, PieceType.rook));
			for(int i=0;i<8;i++) {
				setIcon(1, i, new Piece(PlayerColor.black, PieceType.pawn));
				setIcon(6, i, new Piece(PlayerColor.white, PieceType.pawn));
			}
			setIcon(7, 0, new Piece(PlayerColor.white, PieceType.rook));
			setIcon(7, 1, new Piece(PlayerColor.white, PieceType.knight));
			setIcon(7, 2, new Piece(PlayerColor.white, PieceType.bishop));
			setIcon(7, 3, new Piece(PlayerColor.white, PieceType.queen));
			setIcon(7, 4, new Piece(PlayerColor.white, PieceType.king));
			setIcon(7, 5, new Piece(PlayerColor.white, PieceType.bishop));
			setIcon(7, 6, new Piece(PlayerColor.white, PieceType.knight));
			setIcon(7, 7, new Piece(PlayerColor.white, PieceType.rook));
			for(int i=0;i<8;i++) {
				for(int j=0;j<8;j++) unmarkPosition(i, j);
			}
			onInitiateBoard();
		}

		private boolean firstClk, end;
		PlayerColor turn;
		Piece firstPc;
		Point firstPt;
		ArrayList<Point> Moveable;

		// Inner class to handle button (square) actions.
		class ButtonListener implements ActionListener {
			Point curr;

			ButtonListener(int x, int y) { curr = new Point(x, y); }

			// Handles the action performed when a button (square) is clicked.
			public void actionPerformed(ActionEvent e) {
				if(end) return;

				Piece Pc = getIcon(curr.x, curr.y);
				boolean chgPc = false;

				if(!firstClk)
					chgPc = isAlly(curr.x, curr.y, firstPc.color);

				if(firstClk || chgPc) {
					if(Pc.color != turn) return;

					if(chgPc) {
						for(Point p : Moveable)
							unmarkPosition(p.x, p.y);
						Moveable = new ArrayList<>();
					}

					switch (Pc.type) {
						case pawn:
							markPawn(curr.x, curr.y, Pc.color);
							break;
						case rook:
							markRook(curr.x, curr.y, Pc.color);
							break;
						case knight:
							markKnight(curr.x, curr.y, Pc.color);
							break;
						case bishop:
							markBishop(curr.x, curr.y, Pc.color);
							break;
						case queen:
							markQueen(curr.x, curr.y, Pc.color);
							break;
						case king:
							markKing(curr.x, curr.y, Pc.color);
							break;
						case none:
							break;
					}

					for(Point p : Moveable)
						markPosition(p.x, p.y);

					firstPc = Pc;
					firstPt = curr;
					firstClk = false;
				}
				else {
					Point secondPt = curr;
					boolean isValid = false;

					for(Point p : Moveable) {
						if(p.equals(secondPt)) {
							isValid = true;
							setIcon(curr.x, curr.y, firstPc);
							setIcon(firstPt.x, firstPt.y, new Piece());
							for(Point p0 : Moveable)
								unmarkPosition(p0.x, p0.y);

							firstClk = true;
							Moveable = new ArrayList<>();
							turn = (turn == PlayerColor.black) ? PlayerColor.white : PlayerColor.black;

							if (findKing(turn) == null) {
							    end = true;
							    String winner = (turn == PlayerColor.black) ? "WHITE" : "BLACK";  // Adjust if more players are involved
							    setStatus(winner + " WON / GAME OVER");
							    break;
							}


							String s1 = "";
							String s2 = "";

							if(isCheck(turn, findKing(turn))) {
								s1 = "/ CHECK";
								if(isCheckMate(turn)) {
									s2 = "MATE / GAME OVER";
									end = true;
								}
							}

							setStatus(turn+"'s turn " + s1 + s2);
							break;
						}
					}

					if(!isValid) {
						for(Point p : Moveable)
							unmarkPosition(p.x, p.y);
						firstClk = true;
						Moveable = new ArrayList<>();
					}
				}
			}
		}
		
		// Method to be called upon initiating the board.
		void onInitiateBoard() {
			turn = PlayerColor.white;
			firstClk = true;
			Moveable = new ArrayList<>();
			setStatus(turn + "'s turn");
			end = false;
		}

		// Marks the possible moves for a pawn.
		void markPawn(int x, int y, PlayerColor pc) {
			int move = (pc == PlayerColor.black) ? 1 : -1;
			if((x==7 && pc == PlayerColor.black) || (x == 0 && pc == PlayerColor.white))
				return;
			Piece advance = getIcon(x+move, y);
			if(advance.type == PieceType.none) {
				Moveable.add(new Point(x+move, y));
			}
			if((pc == PlayerColor.black && x == 1) || (pc == PlayerColor.white && x == 6)) {
				Piece advanceTwo = getIcon(x+2*move, y);
				if(advance.type == PieceType.none && advanceTwo.type == PieceType.none)
					Moveable.add(new Point(x+2*move, y));
			}

			switch (y) {
				case 0:
					if(isEnemy(x+move, y+1, pc)) {
						Moveable.add(new Point(x+move, y+1));
					}
					break;
				case 7:
					if(isEnemy(x+move, y-1, pc)) {
						Moveable.add(new Point(x+move, y-1));
					}
					break;
				default:
					if(isEnemy(x+move, y+1, pc)) {
						Moveable.add(new Point(x+move, y+1));
					}
					if(isEnemy(x+move, y-1, pc)) {
						Moveable.add(new Point(x+move, y-1));
					}
					break;
			}

			if(Moveable.isEmpty())
				return;
		}

		// Marks the possible moves for a rook.
		void markRook(int x, int y, PlayerColor pc) {
			for(int i=y+1; i<8; ++i) {
				if(getIcon(x, i).type == PieceType.none)
					Moveable.add(new Point(x, i));
				if(isAlly(x, i, pc))
					break;
				if(isEnemy(x, i, pc)) {
					Moveable.add(new Point(x, i));
					break;
				}
			}
			for(int i=y-1; i>=0; --i) {
				if(getIcon(x,i).type == PieceType.none)
					Moveable.add(new Point(x, i));
				if(isAlly(x, i, pc))
					break;
				if(isEnemy(x, i, pc)) {
					Moveable.add(new Point(x, i));
					break;
				}
			}
			for(int i=x-1; i>=0; --i) {
				if(getIcon(i, y).type == PieceType.none)
					Moveable.add(new Point(i, y));
				if(isAlly(i, y, pc))
					break;
				if(isEnemy(i, y, pc)) {
					Moveable.add(new Point(i, y));
					break;
				}
			}
			for(int i=x+1; i<8; ++i) {
				if(getIcon(i, y).type == PieceType.none)
					Moveable.add(new Point(i, y));
				if(isAlly(i, y, pc))
					break;
				if(isEnemy(i, y, pc)) {
					Moveable.add(new Point(i, y));
					break;
				}
			}
			if(Moveable.isEmpty()) return;
		}

		// Marks the possible moves for a knight.
		void markKnight(int x, int y, PlayerColor pc) {
			for(int i=x-2; i<=x+2; i+=4) {
				for(int j=y-1; j<=y+1; j+=2) {
					if((i>=0) && (i<8) && (j>=0) && (j<8)) {
						if(!isAlly(i, j, pc)) Moveable.add(new Point(i, j));
					}
				}
			}

			for(int i=x-1; i<=x+1; i+=2) {
				for(int j=y-2; j<=y+2; j+=4) {
					if((i>=0) && (i<8) && (j>=0) && (j<8)) {
						if(!isAlly(i, j, pc)) Moveable.add(new Point(i, j));
					}
				}
			}
		}

		// Marks the possible moves for a bishop.
		void markBishop(int x, int y, PlayerColor pc) {
			for(int i=x-1, j=y+1; i>=0 && j<8; --i, ++j) {
				if(getIcon(i, j).type == PieceType.none)
					Moveable.add(new Point(i, j));
				if(isAlly(i, j, pc))
					break;
				if(isEnemy(i, j, pc)) {
					Moveable.add(new Point(i, j));
					break;
				}
			}
			for(int i=x+1, j=y+1; i<8 && j<8; ++i, ++j) {
				if(getIcon(i, j).type == PieceType.none)
					Moveable.add(new Point(i, j));
				if(isAlly(i, j, pc))
					break;
				if(isEnemy(i, j, pc)) {
					Moveable.add(new Point(i, j));
					break;
				}
			}
			for(int i=x-1, j=y-1; i>=0 && j>=0; --i, --j) {
				if(getIcon(i, j).type == PieceType.none)
					Moveable.add(new Point(i, j));
				if(isAlly(i, j, pc))
					break;
				if(isEnemy(i, j, pc)) {
					Moveable.add(new Point(i, j));
					break;
				}
			}
			for(int i=x+1, j=y-1; i<8 && j>=0; ++i, --j) {
				if(getIcon(i, j).type == PieceType.none)
					Moveable.add(new Point(i, j));
				if(isAlly(i, j, pc))
					break;
				if(isEnemy(i, j,pc)) {
					Moveable.add(new Point(i, j));
					break;
				}
			}
			if(Moveable.isEmpty())
				return;
		}

		// Marks the possible moves for a queen.
		void markQueen(int x, int y, PlayerColor pc) {
			markRook(x, y, pc);
			markBishop(x, y, pc);
		}

		// Marks the possible moves for a king.
		void markKing(int x, int y, PlayerColor pc) {
			for(int i=x-1; i<=x+1; ++i) {
				for(int j=y-1; j<=y+1; ++j) {
					if((i>=0) && (i<8) && (j>=0) && (j<8)) {
						if(!isAlly(i, j, pc))
							Moveable.add(new Point(i, j));
					}
				}
			}
		}

		// Determines if a given square is occupied by an ally piece.
		boolean isAlly(int x, int y, PlayerColor pc) {
			return getIcon(x, y).color == pc;
		}

		// Determines if a given square is occupied by an enemy piece.
		boolean isEnemy(int x, int y, PlayerColor pc) {
			return getIcon(x, y).color != pc && getIcon(x, y).color != PlayerColor.none;
		}

		// Finds the position of the king of a given color.
		Point findKing(PlayerColor pc) {
			for(int i=0; i<8; ++i) {
				for(int j=0; j<8; ++j) {
					if(isAlly(i, j, pc) && getIcon(i, j).type == PieceType.king)
						return new Point(i, j);
				}
			}
			return null;
		}

		// Checks if the king of a given color is in check.
		boolean isCheck(PlayerColor pc, Point king) {
			Point kingPosition = king;

			for(int i = kingPosition.x-2; i <= kingPosition.x+2; i += 4) {
				for(int j = kingPosition.y-1; j <= kingPosition.y+1; j += 2) {
					if((i>=0) && (i<8) && (j>=0) && (j<8)) {
						if(isEnemy(i, j, pc) && getIcon(i, j).type == PieceType.knight)
							return true;
					}
				}
			}

			for(int i = kingPosition.x-1; i <= kingPosition.x+1; i += 2) {
				for(int j = kingPosition.y-2; j <= kingPosition.y+2; j += 4) {
					if((i>=0) && (i<8) && (j>=0) && (j<8)) {
						if(isEnemy(i, j, pc) && getIcon(i, j).type == PieceType.knight)
							return true;
					}
				}
			}

			for(int x = kingPosition.x-1; x >= 0; --x) {
				if(isAlly(x, kingPosition.y, pc))
					break;
				if(isEnemy(x,kingPosition.y, pc) && ((getIcon(x, kingPosition.y).type == PieceType.rook) || (getIcon(x, kingPosition.y).type == PieceType.queen))) {
					return true;
				}
			}

			for(int x = kingPosition.x+1; x<8; ++x) {
				if(isAlly(x, kingPosition.y, pc))
					break;
				if(isEnemy(x, kingPosition.y, pc) && ((getIcon(x, kingPosition.y).type == PieceType.rook) || (getIcon(x, kingPosition.y).type == PieceType.queen))) {
					return true;
				}
			}
			
			for(int y = kingPosition.y+1; y<8; ++y) {
				if(isAlly(kingPosition.x, y, pc))
					break;
				if(isEnemy(kingPosition.x, y, pc) && ((getIcon(kingPosition.x, y).type == PieceType.rook) || (getIcon(kingPosition.x, y).type == PieceType.queen))) {
					return true;
				}
			}

			for(int y = kingPosition.y-1; y>=0; --y) {
				if(isAlly(kingPosition.x, y, pc))
					break;
				if(isEnemy(kingPosition.x, y, pc) && ((getIcon(kingPosition.x, y).type == PieceType.rook) || (getIcon(kingPosition.x, y).type == PieceType.queen))) {
					return true;
				}
			}

			for(int x = kingPosition.x-1, y = kingPosition.y-1; x>=0 && y>=0; --x, --y) {
				if(isAlly(x, y, pc))
					break;
				if(pc == PlayerColor.white && x == kingPosition.x-1) {
					if(isEnemy(x, y, pc) && getIcon(x, y).type == PieceType.pawn)
						return true;
				}
				if((isEnemy(x, y, pc)) && ((getIcon(x, y).type == PieceType.bishop) || getIcon(x, y).type == PieceType.queen)) {
					return true;
				}
			}

			for(int x = kingPosition.x+1, y = kingPosition.y-1; x<8 && y>=0; ++x, --y) {
				if(isAlly(x, y, pc))
					break;

				if(pc == PlayerColor.black && x == kingPosition.x+1) {
					if(isEnemy(x, y, pc) && getIcon(x, y).type == PieceType.pawn)
						return true;
				}

				if((isEnemy(x, y, pc)) && ((getIcon(x, y).type == PieceType.bishop) || getIcon(x, y).type == PieceType.queen)) {
					return true;
				}
			}

			for(int x = kingPosition.x-1, y = kingPosition.y+1; x >=0 && y<8; --x, ++y) {
				if(isAlly(x,y,pc))
					break;

				if(pc == PlayerColor.white && x == kingPosition.x-1) {
					if(isEnemy(x, y, pc) && getIcon(x,y).type == PieceType.pawn)
						return true;
				}

				if((isEnemy(x, y, pc)) && ((getIcon(x, y).type == PieceType.bishop) || getIcon(x, y).type == PieceType.queen)) {
					return true;
				}
			}

			for(int x = kingPosition.x+1, y = kingPosition.y+1; x<8 && y<8; ++x, ++y) {
				if(isAlly(x, y, pc))
					break;

				if(pc == PlayerColor.black && x == kingPosition.x+1) {
					if(isEnemy(x, y, pc) && getIcon(x, y).type == PieceType.pawn)
						return true;
				}

				if((isEnemy(x, y, pc)) && ((getIcon(x, y).type == PieceType.bishop) || getIcon(x, y).type == PieceType.queen)) {
					return true;
				}
			}

			return false;
		}

		// Checks if the king of a given color is in checkmate.
		boolean isCheckMate(PlayerColor pc) {
			Point kingPosition = findKing(pc);
			boolean isMate = true;

			for(int i=0; i<8; ++i) {
				for(int j=0; j<8; ++j) {
					if(isAlly(i,j,pc)) {
						switch(getIcon(i, j).type) {
							case pawn:
								markPawn(i, j, pc);
								break;
							case rook:
								markRook(i, j, pc);
								break;
							case knight:
								markKnight(i, j, pc);
								break;
							case bishop:
								markBishop(i, j, pc);
								break;
							case queen:
								markQueen(i, j, pc);
								break;
							case king:
								markKing(i, j, pc);
								break;
						}

						for(Point p : Moveable) {
							Piece originalPiece = getIcon(p.x, p.y);
							setIcon(p.x, p.y, getIcon(i, j));
							setIcon(i, j, new Piece());

							if(!isCheck(pc, findKing(pc))) {
								isMate = false;
							}

							setIcon(i, j, getIcon(p.x, p.y));
							setIcon(p.x, p.y, originalPiece);
						}
						Moveable = new ArrayList<>();
					}
				}
			}

			return isMate;
		}
	}
