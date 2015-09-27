require_relative 'wordwrap.rb'

# Text: aaa bb cc ddddd.
# line_width=6

# Greedy:
# aaa bb| line cost=0
# cc    | line cost=4*4*4=64
# ddddd.| line cost=0
# Total cost = 64

# Dynamic:
# aaa   | line cost=3*3*3=27
# bb cc | line cost=1*1*1=1
# ddddd.| line cost=0
# Total cost = 28

describe Wordwrap do
  it "should calculate cost matrix" do
    wordwrap = Wordwrap.new("wordwrap_test.txt", 6)
    wordwrap.build_cost_matrix

    INF = Float::INFINITY
    expect(wordwrap.lines_cost_matrix).to eq(
    [[27,  0,   INF, INF],
     [nil, 64,    1, INF],
     [nil, nil,  64, INF],
     [nil, nil, nil,   0]])
  end

# Dynamic algorithm progression:
# ---------------------
# Cost of including 1 word (j=1)
# Min cost: c[1]=27; First word of the last line: p[1]=1
# ---------------------
# j=1 (last word), i=1 (first word)
# (cost of first word on one line)
# c[1]=c[0]+27=27

# ---------------------
# Cost of including 2 words (j=2)
# Min cost: c[2]=0; First word of the last line: p[2]=1
# ---------------------
# j=2, i=1
# (cost of first 2 words on one line)
# c[2]=c[0]+0=0
# ---------------
# j=2, i=2
# (cost of 2nd word on one line)
# c[2]=c[1]+64=27+64=91

# ---------------------
# Cost of including 3 words (j=3)
# Min cost: c[3]=28; First word of the last line: p[3]=2
# ---------------------
# j=3, i=1 (on one line)
# c[3]=c[0]+INF=INF (will not fit)
# ----------------
# j=3, i=2
# (cost of 2nd and 3rd words on one line)
# c[3]=c[1]+1=28
# ----------------
# j=3, i=3 (cost of 3rd word on one line)
# c[3]=c[2]+64=64

# ---------------------
# Cost of including 4 words (j=4)
# Min cost: c[4]=28; First word of the last line: p[4]=4
# ---------------------
# j=4, i=1 (on one line)
# c[4]=c[0]+INF=INF (will not fit)
# ----------------
# j=4, i=2 (cost of 2nd, 3rd, 4th words on one line)
# c[4]=c[1]+INF=INF (will not fit)
# ----------------
# j=4, i=3 (cost of 3rd and 4th word on one line)
# c[4]=c[2]+INF=INF (will not fit)
# ----------------
# j=4, i=4 (cost of 4th word on one line)
# c[4]=c[3]+0=28

# c=[27,0,28,28]
# p=[1, 1, 2, 4]

  it "should calculate minimum cost for including all words" do
    wordwrap = Wordwrap.new("wordwrap_test.txt", 6)
    wordwrap.build_cost_matrix
    wordwrap.calculate_min_cost
    expect(wordwrap.min_cost).to eq([27,0,28,28])
  end

  it "should store the index of the first word on the last line" do
    wordwrap = Wordwrap.new("wordwrap_test.txt", 6)
    wordwrap.build_cost_matrix
    wordwrap.calculate_min_cost
    expect(wordwrap.last_line_start).to eq([0,0,1,3]) # ==[1,1,2,4], but init index=0
  end

  it "should determine textlines" do
    wordwrap = Wordwrap.new("wordwrap_test.txt", 6)
    wordwrap.build_cost_matrix
    wordwrap.calculate_min_cost
    wordwrap.determine_textlines(wordwrap.word_count-1)
    expect(wordwrap.textlines).to eq([[0,0],[1,2],[3,3]])
  end

  it "should determine textlines using the greedy algorithm" do
    wordwrap = Wordwrap.new("wordwrap_test.txt", 6)
    wordwrap.build_cost_matrix
    wordwrap.calculate_min_cost
    wordwrap.determine_textlines_greedy
    expect(wordwrap.textlines_greedy).to eq([[0,1],[2,2],[3,3]])
  end
end