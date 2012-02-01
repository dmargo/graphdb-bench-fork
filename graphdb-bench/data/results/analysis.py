#!/usr/bin/python

import sys
import os.path

if __name__ == '__main__':
    if len(sys.argv) == 2:
        main(sys.argv[1])
    else:
        print 'usage: script.py filename'

def main(filename):
    for line in open(filename, 'r'):
        _,name,_,_,time,result,_ = line.split(';')

        if name == 'OperationGetFirstNeighbor':
            op_get_first_neighbor(time, result)
        elif name == 'OperationGetRandomNeighbor':
            op_get_random_neighbor(time, result)
        elif name == 'OperationGetAllNeighbors':
            op_get_all_neighbors(time, result)
        elif name == 'OperationGetKFirstNeighbors':
            op_get_k_first_neighbors(time, result)
        elif name == 'OperationGetKRandomNeighbors':
            op_get_k_random_neighbors(time, result)
        elif name == 'OperationGetKHopNeighbors':
            op_get_k_hop_neighbors(time, result)
        elif name == 'OperationGetShortestPath':
            op_get_shortest_path(time, result)
        elif name == 'OperationGetShortestPathProperty':
            op_get_shortest_path_property(time, result)

    dirname,_ = os.path.splitext(filename):

    print_get_first_neighbor(dirname + '/get_first_neighbor')
    print_get_random_neighbor(dirname + '/get_random_neighbor')
    print_get_all_neighbors(dirname + '/get_all_neighbors')
    print_get_k_first_neighbors(dirname + '/get_k_first_neighbors')
    print_get_k_random_neighbors(dirname + '/get_k_random_neighbors')
    print_get_k_hop_neighbors(dirname + '/get_k_hop_neighbors')
    print_get_shortest_path(dirname + '/get_shortest_path')
    print_get_shortest_path_property(dirname + '/get_shortest_path_property')


op_get_first_neighbor_sum = 0
op_get_first_neighbor_total = 0
op_get_first_neighbor_dne_sum = 0
op_get_first_neighbor_dne_total = 0

def op_get_first_neighbor(time, result):
    time = int(time)

    if result != 'DNE':
        op_get_first_neighbor_sum += time
        op_get_first_neighbor_total += 1
    else
        op_get_first_neighbor_dne_sum += time
        op_get_first_neighbor_dne_total += 1

def print_get_first_neighbor(filename):
    f = open(filename, 'w')

    f.write('mean_without_dne;mean_dne;mean\n')
    f.write(op_get_first_neighbor_sum / op_get_first_neighbor_total + ';')
    f.write(op_get_first_neighbor_dne_sum / op_get_first_neighbor_dne_total + ';')
    f.write((op_get_first_neighbor_total + op_get_first_neighbor_dne_total) / (op_get_first_neighbor_sum + op_get_first_neighbor_dne_sum) + '\n')


op_get_random_neighbor_sum = 0
op_get_random_neighbor_total = 0
op_get_random_neighbor_dne_sum = 0
op_get_random_neighbor_dne_total = 0

def op_get_random_neighbor(time, result):
    time = int(time)

    if result != 'DNE':
        op_get_random_neighbor_sum += time
        op_get_random_neighbor_total += 1
    else
        op_get_random_neighbor_dne_sum += time
        op_get_random_neighbor_dne_total += 1

def print_get_random_neighbor():
    f = open(filename, 'w')

    f.write('mean_without_dne;mean_dne;mean\n')
    f.write(op_get_random_neighbor_sum / op_get_random_neighbor_total + ';')
    f.write(op_get_random_neighbor_dne_sum / op_get_random_neighbor_dne_total + ';')
    f.write((op_get_random_neighbor_total + op_get_random_neighbor_dne_total) / (op_get_random_neighbor_sum + op_get_first_random_dne_sum) + '\n')


op_get_all_neighbors_list = []
op_get_all_neighbors_sum = 0
op_get_all_neighbors_weighted_sum = 0
op_get_all_neighbors_weighted_total = 0

def op_get_all_neighbors(time, result):
    time = int(time)
    result = int(result)

    op_get_all_neighbors_list.append(result)

    op_get_all_neighbors_sum += time
    op_get_all_neighbors_weighted_sum += time / result
    op_get_all_neighbors_weighted_total += result


def print_get_all_neighbors():
    f = open(filename, 'w')

    f.write('mean;weighted_mean_per_op;weighted_mean_over_total\n')
    f.write(op_get_all_neighbors_sum / len(op_get_all_neighbors_list) + ';')
    f.write(op_get_all_neighbors_weighted_sum / len(op_get_all_neighbors_list) + ';')
    f.write(op_get_all_neighbors_sum / op_get_all_neighbors_weighted_total + '\n')

    f.write('time;neighbor_count')
    for (time, result) in op_get_all_neighbors_list:
        f.write(time + ';' + result + '\n')
    

op_get_k_first_neighbors_list = []
op_get_k_first_neighbors_sum = 0
op_get_k_first_neighbors_weighted_sum = 0
op_get_k_first_neighbors_weighted_total = 0

def op_get_k_first_neighbors(time, result):
    time = int(time)
    result = int(result)

    op_get_k_first_neighbors_list.append(result)

    op_get_k_first_neighbors_sum += time
    op_get_k_first_neighbors_weighted_sum += time / result
    op_get_k_first_neighbors_weighted_total += result

def print_get_k_first_neighbors():
    f = open(filename, 'w')

    f.write('mean;weighted_mean_per_op;weighted_mean_over_total\n')
    f.write(op_get_k_first_neighbors_sum / len(op_get_k_first_neighbors_list) + ';')
    f.write(op_get_k_first_neighbors_weighted_sum / len(op_get_k_first_neighbors_list) + ';')
    f.write(op_get_k_first_neighbors_sum / op_get_k_first_neighbors_weighted_total + '\n')

    f.write('time;neighbor_count')
    for (time, result) in op_get_k_first_neighbors_list:
        f.write(time + ';' + result + '\n')


op_get_k_random_neighbors_list = []
op_get_k_random_neighbors_sum = 0
op_get_k_random_neighbors_weighted_by_nodecount_sum = 0
op_get_k_random_neighbors_weighted_by_getcount_sum = 0
op_get_k_random_neighbors_nodecount_total = 0
op_get_k_random_neighbors_getcount_total = 0

def op_get_k_random_neighbors(time, result):
    time = int(time)
    (nodecount, getcount) = result.split(':')
    nodecount = int(nodecount)
    getcount = int(getcount)

    op_get_k_random_neighbors_list.append((time, nodecount, getcount))

    op_get_k_random_neighbors_sum += time
    op_get_k_random_neighbors_weighted_by_nodecount_sum += time / nodecount
    op_get_k_random_neighbors_weighted_by_getcount_sum += time / getcount
    op_get_k_random_neighbors_nodecount_total += nodecount
    op_get_k_random_neighbors_getcount_total += getcount

def print_get_k_random_neighbors():
    f = open(filename, 'w')

    f.write('mean;weighted_by_nodecount_per_op;weighted_by_nodecount_over_total;weighted_by_getcount_per_op;weighted_by_getcount_over_total')
    f.write(op_get_k_random_neighbors_sum / len(op_get_k_random_neighbors_list) + ';')
    f.write(op_get_k_random_neighbors_weighted_by_nodecount_sum / len(op_get_k_random_neighbors_list) + ';')
    f.write(op_get_k_random_neighbors_sum / op_get_k_random_neighbors_nodecount_total + ';')
    f.write(op_get_k_random_neighbors_weighted_by_getcount_sum / len(op_get_k_random_neighbors_list) + ';')
    f.write(op_get_k_random_neighbors_sum / op_get_k_random_neighbors_getcount_total + '\n')

    f.write('time;nodecount;getcount')
    for (time, nodecount, getcount) in op_get_k_random_neighbors_list:
        f.write(time + ';' + nodecount + ';' + getcount + '\n')


op_get_k_hop_neighbors_list = []
op_get_k_hop_neighbors_sum = 0
op_get_k_hop_neighbors_weighted_by_nodecount_sum = 0
op_get_k_hop_neighbors_weighted_by_getcount_sum = 0
op_get_k_hop_neighbors_nodecount_total = 0
op_get_k_hop_neighbors_getcount_total = 0

def op_get_k_hop_neighbors(time, result):
    time = int(time)
    (nodecount, getcount) = result.split(':')
    nodecount = int(nodecount)
    getcount = int(getcount)

    op_get_k_hop_neighbors_list.append((time, nodecount, getcount))

    op_get_k_hop_neighbors_sum += time
    op_get_k_hop_neighbors_weighted_by_nodecount_sum += time / nodecount
    op_get_k_hop_neighbors_weighted_by_getcount_sum += time / getcount
    op_get_k_hop_neighbors_nodecount_total += nodecount
    op_get_k_hop_neighbors_getcount_total += getcount

def print_get_k_hop_neighbors():
    f = open(filename, 'w')

    f.write('mean;weighted_by_nodecount_per_op;weighted_by_nodecount_over_total;weighted_by_getcount_per_op;weighted_by_getcount_over_total')
    f.write(op_get_k_hop_neighbors_sum / len(op_get_k_hop_neighbors_list) + ';')
    f.write(op_get_k_hop_neighbors_weighted_by_nodecount_sum / len(op_get_k_hop_neighbors_list) + ';')
    f.write(op_get_k_hop_neighbors_sum / op_get_k_hop_neighbors_nodecount_total + ';')
    f.write(op_get_k_hop_neighbors_weighted_by_getcount_sum / len(op_get_k_hop_neighbors_list) + ';')
    f.write(op_get_k_hop_neighbors_sum / op_get_k_hop_neighbors_getcount_total + '\n')

    f.write('time;nodecount;getcount')
    for (time, nodecount, getcount) in op_get_k_hop_neighbors_list:
        f.write(time + ';' + nodecount + ';' + getcount + '\n')


op_get_shortest_path_list = []
op_get_shortest_path_sum = 0
op_get_shortest_path_weighted_by_pathlen_sum = 0
op_get_shortest_path_weighted_by_getcount_sum = 0
op_get_shortest_path_weighted_by_nodecount_sum = 0
op_get_shortest_path_pathlen_total = 0
op_get_shortest_path_getcount_total = 0
op_get_shortest_path_nodecount_total = 0

def op_get_shortest_path(time, result):
    time = int(time)
    (pathlen, getcount, nodecount) = result.split(':')
    pathlen = int(pathlen)
    getcount = int(getcount)
    nodecount = int(nodecount)

    op_get_shortest_path_list.append((time, pathlen, getcount, nodecount))

    op_get_shortest_path_sum += time
    op_get_shortest_path_weighted_by_pathlen_sum += time / pathlen
    op_get_shortest_path_weighted_by_getcount_sum += time / getcount
    op_get_shortest_path_weighted_by_nodecount_sum += time / nodecount
    op_get_shortest_path_pathlen_total += pathlen
    op_get_shortest_path_getcount_total += getcount
    op_get_shortest_path_nodecount_total += nodecount

def print_get_shortest_path():
    f = open(filename, 'w')

    f.write('mean;weighted_by_pathlen_per_op;weighted_by_pathlen_over_total;weighted_by_getcount_per_op;weighted_by_getcount_over_total;weighted_by_nodecount_per_op;weighted_by_nodecount_over_total')
    f.write(op_get_shortest_path_sum / len(op_get_shortest_path_list) + ';')
    f.write(op_get_shortest_path_weighted_by_pathlen_sum / len(op_get_shortest_path_list) + ';')
    f.write(op_get_shortest_path_sum / op_get_shortest_path_pathlen_total + ';')
    f.write(op_get_shortest_path_weighted_by_getcount_sum / len(op_get_shortest_path_list) + ';')
    f.write(op_get_shortest_path_sum / op_get_shortest_path_getcount_total + ';')
    f.write(op_get_shortest_path_weighted_by_nodecount_sum / len(op_get_shortest_path_list) + ';')
    f.write(op_get_shortest_path_sum / op_get_shortest_path_nodecount_total + ';')

    f.write('time;pathlen;getcount;nodecount')
    for (time, pathlen, getcount, nodecount) in op_get_shortest_path_list:
        f.write(time + ';' + pathlen + ';' + getcount + ';' + nodecount + '\n')

def op_get_shortest_path_property(time, result):
def print_get_shortest_path_property():
