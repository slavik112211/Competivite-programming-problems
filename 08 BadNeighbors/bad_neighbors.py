#python -m pdb bad_neighbors.py
#taken from http://gceprogrammers.blogspot.ca/2014/03/bad-neighbors.html
import pdb;

donations = [int(x) for x in raw_input().split()] #input array
max_don = [x for x in donations]                  #DP array
zero_used = [0 for x in donations]
zero_used[0] = 1
for i in range(1,len(donations)):
 for j in range(i): 

  pdb.set_trace()
  if (zero_used[j] == 1) and (i == (len(donations) - 1)):
   if (donations[i]+max_don[j]-max_don[0] > max_don[i]) and (j!=i-1):
    max_don[i] = donations[i]+max_don[j]-max_don[0]
    zero_used[i] = 1

  elif (j == i-1):
   if max_don[j] > max_don[i]:
    max_don[i] = max_don[j]
    if zero_used[j] == 1:
     zero_used[i] = 1
    else:
     zer_used[i] = 0

  else:
   if donations[i] + max_don[j] > max_don[i]:
    max_don[i] = donations[i] + max_don[j]
    if zero_used[j] == 1:
     zero_used[i] = 1
    else:
     zero_used[i] = 0

print max(max_don)