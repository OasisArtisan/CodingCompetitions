# https://csacademy.com/contest/archive/task/virus-on-a-tree/


class Node:
    def __init__(self, index):
        self.index = index
        self.parent = None
        self.height = None
        self.height_below = None
        self.family_size = None  # Including the node itself
        self.children = set()
        self.connections = set()

    def __str__(self):
        return "{}: Parent: {} Children: {} Connections: {} Family Size: {} lvltop: {} lvlbot: {}".format(
            self.index, self.parent, self.children, self.connections, self.family_size, self.height, self.height_below)


class Edge:
    def __init(self, n1, n2, can_cut):
        self.n1 = n1
        self.n2 = n2
        self.can_cut = can_cut


N, K = [int(x) for x in input().split()]
can_cut = dict()
nodes = [Node(i) for i in range(N+1)]
for e in range(N-1):
    inp = input().split()
    n1 = int(inp[0])
    n2 = int(inp[1])
    c = inp[2] == "1"
    nodes[n1].connections.add(n2)
    nodes[n2].connections.add(n1)
    can_cut[frozenset({n1, n2})] = c


def info_traverse(height, parent_index, root_node):
    root_node.parent = parent_index
    if parent_index is not None:
        root_node.children = root_node.connections - {parent_index}
    else:
        root_node.children = root_node.connections
    family_size = 0
    height_below = 1
    for child_index in root_node.children:
        r1, r2 = info_traverse(height + 1, root_node.index, nodes[child_index])
        family_size += r1
        height_below = max(r2, height_below)
    root_node.family_size = family_size + 1
    root_node.height_below = height_below
    root_node.height = height
    return root_node.family_size, root_node.height_below + 1


def bfs(fun):
    i = 0
    current_level = [1]
    next_level = []
    while i < len(current_level):
        node = nodes[current_level[i]]
        fun(node.index)
        next_level.extend(node.children)
        i += 1
        if i == len(current_level):
            current_level = next_level
            next_level = list()
            i = 0


info_traverse(1, None, nodes[1])
bfs(print)


def solve(node):
    pass