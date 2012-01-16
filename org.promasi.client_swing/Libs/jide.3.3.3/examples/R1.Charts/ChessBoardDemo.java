/*
 * @(#)ChessBoardDemo.java 24-Jul-2010
 *
 * 2002 - 2011 JIDE Software Inc. All rights reserved.
 * Copyright 2005 - 2011 Catalysoft Ltd. All rights reserved.
 */

import com.jidesoft.chart.Chart;
import com.jidesoft.chart.RectangularRegionMarker;
import com.jidesoft.chart.axis.CategoryAxis;
import com.jidesoft.chart.model.*;
import com.jidesoft.chart.render.PointRenderer;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.range.CategoryRange;
import com.jidesoft.range.Positionable;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

@SuppressWarnings("serial")
public class ChessBoardDemo extends AbstractDemo {
    private enum file {a, b, c, d, e, f, g, h}
    private Integer[] ranks = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8};
    private CategoryRange<file> fileRange = new CategoryRange<file>(file.values());
    private CategoryRange<Integer> rankRange = new CategoryRange<Integer>(ranks);
    private Color light = new Color(255, 207, 144);
    private Color dark = new Color(143, 96, 79);
    private Chart chart;
    private ChartPoint3D movingPoint = null;
    private int updatedPointIndex = -1;
    final ChartStyle pointsOnly = new ChartStyle().withPoints();
    private boolean done = false;
    private JPanel controlPanel;
    private volatile boolean playing = false;
    private Thread player = null;
    private JButton setupButton = new JButton("Setup");
    private JButton playButton = new JButton("Play");
    private JPanel demoPanel, optionsPanel;

    public Component getDemoPanel() {
        if (demoPanel == null) {
            demoPanel = new JPanel();
            demoPanel.setLayout(new BoxLayout(demoPanel, BoxLayout.Y_AXIS));

            controlPanel = new JPanel();
            setupButton.setEnabled(false);
            setupButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    DefaultChartModel model = createBoard();
                    chart.setModel(model, pointsOnly);
                    setupButton.setEnabled(false);
                    playButton.setEnabled(true);
                }
            });
            final Runnable run = new Runnable() {
                public void run() {
                    playMoves(Arrays.asList(moves));
                }
            };
            playButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    if (!playing) {
                        if (player == null) {
                            player = new Thread(run);
                        }
                        playButton.setText("Stop");
                        playing = true;
                        if (!player.isAlive()) {
                            player.start();
                        }
                    } else {
                        playButton.setText("Play");
                        playing = false;
                    }
                }
            });
            controlPanel.add(setupButton);
            controlPanel.add(playButton);
            chart = new Chart();
            Dimension size = new Dimension(500, 500);
            chart.setPreferredSize(size);
            chart.setMaximumSize(size);
            chart.setMinimumSize(size);
            CategoryAxis<file> xAxis = new CategoryAxis<file>(fileRange);
            CategoryAxis<Integer> yAxis = new CategoryAxis<Integer>(rankRange);
            chart.setXAxis(xAxis);
            chart.setYAxis(yAxis);
            chart.setHorizontalGridLinesVisible(false);
            chart.setVerticalGridLinesVisible(false);
            chart.setTickLength(0);
            chart.setChartBackground(light);
            // Set up the black and white squares
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    Color fill = (i + j) % 2 == 0 ? dark : light;
                    if ((i + j) % 2 == 0) {
                        chart.addDrawable(new RectangularRegionMarker(chart, i - 0.5, i + 0.5, j - 0.5, j + 0.5, fill));
                    }
                }
            }
            // ...and adjust the minima and maxima to remove unnecessary space
            // around the edge of the board
            fileRange.setMinimum(0.5);
            fileRange.setMaximum(8.5);
            rankRange.setMinimum(0.5);
            rankRange.setMaximum(8.5);

            chart.setPointRenderer(new PointRenderer() {
                public Shape renderPoint(Graphics g, Chart chart, ChartModel m, Chartable point, boolean isSelected,
                        boolean hasRollover, boolean hasFocus, int x, int y) {
                    ChartPoint3D p;
                    if (point instanceof ChartPoint3D) {
                        p = (ChartPoint3D) point;
                    } else {
                        ModelMorpher.TransitionPoint tp = (ModelMorpher.TransitionPoint) point;
                        p = (ChartPoint3D) tp.getSource();
                    }
                    ChessPiece piece = (ChessPiece) p.getZ();
                    ChessPieceType pieceType = piece.getPieceType();
                    int pieceSize = pieceType.getSize();
                    g.drawImage(pieceType.getImage(), x - pieceSize / 2, y - pieceSize / 2, chart);
                    return null;
                }
            });
            demoPanel.add(chart);
            demoPanel.add(controlPanel);
            DefaultChartModel model = createBoard();
            chart.setModel(model, new ChartStyle().withPoints());
        }
        return demoPanel;
    }

    public void playMoves(List<String> moves) {
        assert !SwingUtilities.isEventDispatchThread();
        for (String move : moves) {
            assert move.length() == 5;
            if ("O-O B".equals(move)) { // Black castling king's side
                moveAndAnimatePiece((DefaultChartModel) chart.getModel(), file.e, 8, file.g, 8);
                while (!done) {try {Thread.sleep(300);} catch (InterruptedException e) {}}
                moveAndAnimatePiece((DefaultChartModel) chart.getModel(), file.h, 8, file.f, 8);
            } else if ("O-O W".equals(move)) { // White castling king's side
                moveAndAnimatePiece((DefaultChartModel) chart.getModel(), file.e, 1, file.g, 1);
                while (!done) {try {Thread.sleep(300);} catch (InterruptedException e) {}}
                moveAndAnimatePiece((DefaultChartModel) chart.getModel(), file.h, 1, file.f, 1);
            } else {
                file fromFile = file.valueOf(move.substring(0, 1));
                int fromRank = Integer.parseInt(move.substring(1, 2));
                file toFile = file.valueOf(move.substring(3, 4));
                int toRank = Integer.parseInt(move.substring(4, 5));
                moveAndAnimatePiece((DefaultChartModel) chart.getModel(), fromFile, fromRank, toFile, toRank);
            }
            while (!done) {try {Thread.sleep(300);} catch (InterruptedException e) {}}
            while (!playing) {try {Thread.sleep(300);} catch (InterruptedException e) {}}
        }
        setupButton.setEnabled(true);
        playButton.setText("Play");
        playButton.setEnabled(false);
        playing = false;
        player = null;
    }

    private DefaultChartModel moveAndAnimatePiece(final DefaultChartModel model, file fromFile, int fromRank, file toFile, int toRank) {
        final DefaultChartModel newModel = new DefaultChartModel(model);
        final ChartPoint3D pieceToTake = pieceAt(model, toFile, toRank);
        movePiece(newModel, fromFile, fromRank, toFile, toRank);
        done = false;
        final ModelMorpher morpher = new ModelMorpher(chart, 10, 40);
        morpher.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (ModelMorpher.PROPERTY_MORPH_ENDED.equals(evt.getPropertyName())) {
                    assert SwingUtilities.isEventDispatchThread();
                    if (pieceToTake != null) {
                        int sizeBefore = newModel.getPointCount();
                        newModel.removePoint(pieceToTake);
                        assert newModel.getPointCount() + 1 == sizeBefore;
                    }
                    chart.setModel(newModel, pointsOnly);
                    done = true;
                }
            }
        });
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                chart.removeModel(model);
                morpher.morph(model, pointsOnly, newModel, pointsOnly);
            }
        });

        return newModel;
    }

    private void movePiece(final DefaultChartModel model, final file fromFile, final int fromRank, final file toFile, final int toRank) {
        for (int i=0; i<model.getPointCount(); i++) {
            Chartable c = model.getPoint(i);
            ChartPoint3D p = (ChartPoint3D) c;
            Positionable xPos = p.getX();
            Positionable yPos = p.getY();
            if (xPos.position() == fromFile.ordinal()+1 && yPos.position() == fromRank) {
                movingPoint = p;
                updatedPointIndex = i;
            }
        }
        if (movingPoint == null) {
            throw new IllegalStateException("Piece not found at "+fromFile+fromRank);
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ChartPoint3D p2 = new ChartPoint3D();
                p2.setX(fileRange.getCategoryValues().get(toFile.ordinal()));
                p2.setY(new RealPosition(toRank));
                p2.setZ(movingPoint.getZ());
                model.replacePoint(updatedPointIndex, p2);
            }
        });
    }

    private ChartPoint3D pieceAt(DefaultChartModel model, file f, int rank) {
        for (Chartable c : model) {
            ChartPoint3D point = (ChartPoint3D) c;
            Positionable xPos = point.getX();
            Positionable yPos = point.getY();
            if (xPos.position() == f.ordinal()+1 && yPos.position() == rank) {
                return point;
            }
        }
        return null;
    }


    private DefaultChartModel createBoard() {
        DefaultChartModel model = new DefaultChartModel();
        // White Pieces
        addPiece(model, file.a, 1, ChessPieceType.wr);
        addPiece(model, file.b, 1, ChessPieceType.wn);
        addPiece(model, file.c, 1, ChessPieceType.wb);
        addPiece(model, file.d, 1, ChessPieceType.wq);
        addPiece(model, file.e, 1, ChessPieceType.wk);
        addPiece(model, file.f, 1, ChessPieceType.wb);
        addPiece(model, file.g, 1, ChessPieceType.wn);
        addPiece(model, file.h, 1, ChessPieceType.wr);
        // Black Pieces
        addPiece(model, file.a, 8, ChessPieceType.br);
        addPiece(model, file.b, 8, ChessPieceType.bn);
        addPiece(model, file.c, 8, ChessPieceType.bb);
        addPiece(model, file.d, 8, ChessPieceType.bq);
        addPiece(model, file.e, 8, ChessPieceType.bk);
        addPiece(model, file.f, 8, ChessPieceType.bb);
        addPiece(model, file.g, 8, ChessPieceType.bn);
        addPiece(model, file.h, 8, ChessPieceType.br);
        // Add the pawns
        for (file f : file.values()) {
            addPiece(model, f, 2, ChessPieceType.wp);
            addPiece(model, f, 7, ChessPieceType.bp);
        }
        return model;
    }

    private void addPiece(DefaultChartModel model, file f, int rank, ChessPieceType pieceType) {
        ChartPoint3D piece = new ChartPoint3D();
        piece.setX(fileRange.getCategoryValues().get(f.ordinal()));
        piece.setY(new RealPosition(rank));
        piece.setZ(new ChessPiece(pieceType));
        model.addPoint(piece);
    }
    
    @Override
    public Component getOptionsPanel() {
        if (optionsPanel == null) {
            optionsPanel = new JPanel();
            optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
            final JRadioButton withoutShadowButton = new JRadioButton("Without Shadow");
            final JRadioButton withShadowButton = new JRadioButton("With Shadow");
            optionsPanel.add(withoutShadowButton);
            optionsPanel.add(withShadowButton);
            ButtonGroup shadowGroup = new ButtonGroup();
            shadowGroup.add(withoutShadowButton);
            shadowGroup.add(withShadowButton);
            withoutShadowButton.setSelected(true);
            ActionListener listener = new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    chart.setShadowVisible(withShadowButton.isSelected());
                }
            };
            withShadowButton.addActionListener(listener);
            withoutShadowButton.addActionListener(listener);
        }
        return optionsPanel;
    }

    /**
     * The type of chess piece will be used as the value for the z axis
     * This class wraps an EChessPiece value for the type of the piece and
     * implements Positionable so it can be used as a value for the z axis
     */
    private class ChessPiece implements Positionable {
        private ChessPieceType piece;

        public ChessPiece(ChessPieceType piece) {
            this.piece = piece;
        }

        public ChessPieceType getPieceType() {
            return piece;
        }

        public double position() {
            return piece.ordinal();
        }

        public int compareTo(Positionable o) {
            double pos = position();
            double oPos = o.position();
            if (pos == oPos) {
                return 0;
            } else {
                return pos < oPos ? -1 : 1;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ChessPiece that = (ChessPiece) o;

            if (piece != that.piece) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return piece != null ? piece.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "ChessPiece{" +
                    "piece=" + piece +
                    '}';
        }
    }


    
    private static String[] moves = {
                "e2-e4", "c7-c6",
                "d2-d4", "d7-d5",
                "b1-c3", "d5xe4",
                "f2-f3", "e7-e5",
                "c1-e3", "d8-b6",
                "d1-d2", "b6xb2",
                "a1-b1", "b2-a3",
                "f1-c4", "b7-b5",
                "c3xb5", "c6xb5",
                "c4-d5", "a7-a5",
                "d5xa8", "f8-b4",
                "b1xb4", "a5xb4",
                "a8xe4", "g8-f6",
                "e4-d3", "f6-d5",
                "d4xe5", "d5xe3",
                "d2xe3", "O-O B",
                "g1-e2", "c8-e6",
                "O-O W", "b8-c6",
                "e3-e4", "f8-c8",
                "e4xh7", "g8-f8",
                "h7-h8", "f8-e7",
                "h8xg7", "a3xa2",
                "g7-f6", "e7-e8",
                "e2-f4", "c6-d4",
                "f1-d1", "b4-b3",
                "f4xe6", "b3xc2",
                "d3xb5", "d4xb5", // Actually Black resigned here, because mate follows
                "d1-d8", "c8xd8",
                "f6xd8"
        };

    public String getName() {
        return "Chess Board Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_CHARTS;
    }

    @Override
    public String getDescription() {
        return "This demo helps to show the adaptability of the Chart product. The chess board is implemented " +
        		"as a Chart with categorical x and y axes. The axes are configured to label the rank and "+
        		"file and the chess pieces are the points of a chart model, displayed with a custom renderer."+
        		" The built-in morphing makes it very easy to animate the moves of a game.";
    }

    public static void main(String[] args) throws Exception {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new ChessBoardDemo());
    }
}
