#!/bin/python3
import sys

#sys.path.append("./secret")
import grid_mdp
import random

grid = grid_mdp.Grid_Mdp();
states = grid.getStates();
actions = grid.getActions();
gamma = grid.getGamma();


def mc(gamma, state_sample, action_sample, reward_sample):
    vfunc = dict();
    nfunc = dict();
    for s in states:
        vfunc[s] = 0.0
        nfunc[s] = 0.0

    for iter1 in range(len(state_sample)):
        G = 0.0
        for step in range(len(state_sample[iter1])-1, -1, -1):
            G *= gamma;
            G += reward_sample[iter1][step];
        for step in range(len(state_sample[iter1])):
            s = state_sample[iter1][step]
            vfunc[s] += G;
            nfunc[s] += 1.0;
            G -= reward_sample[iter1][step]
            G /= gamma;

    for s in states:
        if nfunc[s] > 0.000001:
            vfunc[s] /= nfunc[s]

    print("mc")
    print(vfunc)

    return vfunc


def td(alpha, gamma, state_sample, action_sample, reward_sample):
    vfunc = dict()
    for s in states:
        vfunc[s] = random.random()

    for iter1 in range(len(state_sample)):
        for step in range(len(state_sample[iter1])):
            s = state_sample[iter1][step]
            r = reward_sample[iter1][step]

            if len(state_sample[iter1]) - 1 > step:
                s1 = state_sample[iter1][step + 1]
                next_v = vfunc[s1]
            else:
                next_v = 0.0;

            vfunc[s] = vfunc[s] + alpha * (r + gamma * next_v - vfunc[s]);

    print("")
    print("td")
    print(vfunc)

    return vfunc


def tdn(alpha, gamma, state_sample, action_sample, reward_sample):
    vfunc1 = dict()
    vfunc2 = dict()
    j = 0
    for s in states:
        vfunc1[s] = random.random()
        vfunc2[s] = 0

    for iter1 in range(len(state_sample)):
        for step in range(len(state_sample[iter1])):
            s = state_sample[iter1][step]
            r = reward_sample[iter1][step]

            if len(state_sample[iter1]) - 1 > step:
                s1 = state_sample[iter1][step + 1]
                next_v = vfunc1[s1]
            else:
                next_v = 0.0;

            vfunc1[s] = vfunc1[s] + alpha * (r + gamma * next_v - vfunc1[s]);
            j = j + 1
            for i in range(step):
                vfunc2[s] = (vfunc2[s] + (1 - gamma) * (gamma**(i - 1)) * vfunc1[s]);

            vfunc2[s] = vfunc2[s]/j
    print("")
    print("tdn")
    print(vfunc2)

    return vfunc2


def nstep(alpha, gamma, state_sample, action_sample, reward_sample):
    vfunc1 = dict()
    vfunc2 = dict()
    for s in states:
        vfunc1[s] = random.random()
        vfunc2[s] = 0
    for iter1 in range(len(state_sample)):
        for step in range(len(state_sample[iter1])):
            s = state_sample[iter1][step]
            r = reward_sample[iter1][step]

            if len(state_sample[iter1]) - 1 > step:
                s1 = state_sample[iter1][step + 1]
                next_v = vfunc1[s1]
            else:
                next_v = 0.0;
            vfunc1[s] = vfunc1[s] + alpha * (r + gamma * next_v - vfunc1[s]);
            vfunc2[s] = (vfunc2[s] + vfunc1[s]) / 2

    print("")
    print("nstep")
    print(vfunc2)

    return vfunc2

if __name__ == "__main__":
    # s, a, r = grid.gen_randompi_sample(5)
    # print s
    # print a
    # print r

    s, a, r = grid.gen_randompi_sample(1000)#迭代次数
    mc(0.5, s, a, r)
    td(0.15, 0.5, s, a, r)
    tdn(0.15, 0.5, s, a, r)
    nstep(0.15, 0.5, s, a, r)