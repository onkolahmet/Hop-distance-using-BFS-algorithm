# hop_distance_using_BFS_algorithm
calculating hop-distance between devices using adjacency list and BFS algorithm.

Suppose that there are n laptops each containing a wireless transmitter. For each laptop i,
following information are known:
- Position, (xi
, yi),

- Wireless transmission range, ri <br />

That is, we can imagine that the wireless range of laptop i is a circle centered at (xi
, yi) with radius ri . We say that the laptops i and j can communicate if their wireless ranges overlap. <br /><br />
 Of course, not every laptop can communicate with every other laptop, but laptops can send
messages by using intermediate laptops as routers. Hop distance h(i, j) is defined as the
minimum number of intermediate laptops used to send a message from laptop i to laptop j.
<br /><br />For example, if two laptops can communicate directly, the hop distance between them is 1.
