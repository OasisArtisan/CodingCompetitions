# https://csacademy.com/ieeextreme-practice/task/tree-fun/
# Score: 40/100 (2574 ms - 54.3 MB)

nodes = {}
n, m = list(map(int, input().split()))

# Read tree
for i in range(n-1):
    a, b = input().split()
    if a not in nodes:
        nodes[a] = {'n':a,'pt':None,'chld':[],'sc':0}
    if b not in nodes:
        nodes[b] = {'n':b,'pt':None,'chld':[],'sc':0}
    nodes[a]['chld'].append(nodes[b])
    nodes[b]['chld'].append(nodes[a])

# Traverse tree once to generate child parent relationship
unvisited_nodes = set(nodes.keys())
node_stack = [nodes['0']]
while True:
    if len(node_stack) == 0:
        break
    node = node_stack[-1]
    # Is this the first time we reach this node?
    if node['n'] in unvisited_nodes:
        unvisited_nodes.remove(node['n'])
        if len(node_stack) > 1:
            # Remove parent from children to parent attribute
            node['chld'].remove(node_stack[-2])
            node['pt'] = node_stack[-2]
    # Find a child in that node that we still need to visit
    for child in node['chld']:
        if child['n'] in unvisited_nodes:
            node_stack.append(child)
            break
    # If we did not add anything to the stack that means all children
    # Have been visited and we need to go back up
    if node == node_stack[-1]:
        node_stack.pop()


# Start computation
final_score = 0
for i in range(m):
    a,b,k = input().split()
    k = int(k)
    atrail = a
    btrail = b
    node_a = nodes[a]
    node_b = nodes[b]
    while atrail[-1] != '0':
        node_a = node_a['pt']
        atrail += node_a['n']
    while btrail[-1] != '0':
        node_b = node_b['pt']
        btrail += node_b['n']
    complete_path = ''
    for c in atrail:
        complete_path += c
        index = btrail.find(c)
        if index != -1:
            complete_path += btrail[0:index]
            break
    for c in complete_path:
        nodes[c]['sc'] += k
        final_score = max(nodes[c]['sc'],final_score)
print(final_score)


# #Print tree
# for node in nodes.values():
#     out = ''
#     for ch in node['chld']:
#         out += ch['n'] + ', '
#     parent = node['pt']
#     if parent is None:
#         parent = 'None'
#     else:
#         parent = parent['n']
#     print('Node: {} Parent: {} Children: {}'.format(node['n'],parent, out))
#
