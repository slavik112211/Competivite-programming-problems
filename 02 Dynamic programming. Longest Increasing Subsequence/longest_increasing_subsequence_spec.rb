require_relative 'longest_increasing_subsequence.rb'
I = Float::INFINITY

describe LongestIncreasingSubsequence do
  # input = [1,2,3,6,4,5]
  # 1. Index i (outer array) denotes sequence length.
  #    Example i=4, we consider first four elements [1,2,3,6].
  # 2. Index j (inner array) denotes a LIS length.
  #    Example i=4 - in an sequence [1,2,3,6]; j=2 denote LIS of length 2. (sequences [1,2] or [3,6], etc)
  # 3. matrix element value solutions_matrix[i,j] denotes a last element of LIS.
  #    Example: for ex. solutions_matrix[4,3] - in an sequence [1,2,3,6], last element of LIS length=3 is 6: [1,2,6])
  # 4. When there are couple of same-size sequences available, pick the one with the smallest last value.
  #    Example: solutions_matrix[4,3] - in an sequence [1,2,3,6], LIS length=3: [1,2,3], [1,2,6], etc. We should store sequence [1,2,3])
  it "should build a LIS matrix" do
    lis = LongestIncreasingSubsequence.new([1,4,5,2,3,6])
    lis.build_subsolutions
    # row 0, and column 0 serve as init values for the algorithm
    expect(lis.subsolutions).to eq(
    [[-I, I, I, I, I, I, I],
     [-I, 1, I, I, I, I, I],
     [-I, 1, 4, I, I, I, I],
     [-I, 1, 4, 5, I, I, I],
     [-I, 1, 2, 5, I, I, I],
     [-I, 1, 2, 3, I, I, I],
     [-I, 1, 2, 3, 6, I, I]])

    lis.sequence = [1,4,2,3]
    lis.build_subsolutions
    expect(lis.subsolutions).to eq(
    [[-I, I, I, I, I],
     [-I, 1, I, I, I],
     [-I, 1, 4, I, I],
     [-I, 1, 2, I, I],
     [-I, 1, 2, 3, I]])

    lis.sequence = [1,2,4,7,5,3]
    lis.build_subsolutions
    expect(lis.subsolutions).to eq(
    [[-I, I, I, I, I, I, I], 
     [-I, 1, I, I, I, I, I], 
     [-I, 1, 2, I, I, I, I], 
     [-I, 1, 2, 4, I, I, I], 
     [-I, 1, 2, 4, 7, I, I], 
     [-I, 1, 2, 4, 5, I, I], 
     [-I, 1, 2, 3, 5, I, I]])

    lis.sequence = [10,22,9,33,21,50,41,60,80]
    lis.build_subsolutions
    expect(lis.subsolutions).to eq(
    [[-I,  I,  I,  I,  I,  I,  I,  I,  I,  I],
     [-I, 10,  I,  I,  I,  I,  I,  I,  I,  I],
     [-I, 10, 22,  I,  I,  I,  I,  I,  I,  I],
     [-I,  9, 22,  I,  I,  I,  I,  I,  I,  I],
     [-I,  9, 22, 33,  I,  I,  I,  I,  I,  I],
     [-I,  9, 21, 33,  I,  I,  I,  I,  I,  I],
     [-I,  9, 21, 33, 50,  I,  I,  I,  I,  I],
     [-I,  9, 21, 33, 41,  I,  I,  I,  I,  I],
     [-I,  9, 21, 33, 41, 60,  I,  I,  I,  I],
     [-I,  9, 21, 33, 41, 60, 80,  I,  I,  I]])
  end

  it "should determine the length of LIS" do
    lis = LongestIncreasingSubsequence.new([1,4,5,2,3,6])
    lis.build_subsolutions
    expect(lis.find_LIS_length).to eq(4)

    lis.sequence = [1,4,2,3]
    lis.build_subsolutions
    expect(lis.find_LIS_length).to eq(3)

    lis.sequence = [10,22,9,33,21,50,41,60,80]
    lis.build_subsolutions
    expect(lis.find_LIS_length).to eq(6)
  end

  it "should backtrack through subsolutions matrix and generate a LIS" do
    lis = LongestIncreasingSubsequence.new([1,4,5,2,3,6])
    lis.build_subsolutions
    lis.generate_LIS
    expect(lis.solution).to eq([1,2,3,6])

    lis.sequence = [1,4,2,3]
    lis.build_subsolutions
    lis.generate_LIS
    expect(lis.solution).to eq([1,2,3])

    lis.sequence = [10,22,9,33,21,50,41,60,80]
    lis.build_subsolutions
    lis.generate_LIS
    # sequence of 9, LIS of 6: 80
    # sequence of 8, LIS of 5: 60
    # sequence of 7, LIS of 4: 41
    # sequence of 6, LIS of 3: 33 (pos 4; 4-1)
    # sequence of 3, LIS of 2: 22 (pos 2; 2-1)
    # sequence of 1, LIS of 1: 10
    expect(lis.solution).to eq([10,22,33,41,60,80])
  end


end