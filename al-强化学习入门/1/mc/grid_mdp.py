# /bin/python3
import numpy;
import random;


class Grid_Mdp:
    def __init__(self):
        #4*4方格
        self.states = [ 1, 2, 3, 4,
                        5, 6, 7, 8,
                        9,10,11,12,
                       13,14,15,16 ]  # 0 indicates end

        self.terminal_states = dict()
        #self.terminal_states[1] = 1
        self.terminal_states[7] = 1
        self.terminal_states[16] = -1

        self.actions = ['n', 'e', 's', 'w']#上，右，下，左

        self.rewards = dict();
        #遇到阻碍 -1
        self.rewards['3_s'] = -1.0
        self.rewards['6_e'] = -1.0
        self.rewards['8_w'] = -1.0
        self.rewards['11_n'] = -1.0
        #遇到宝藏 +1
        self.rewards['12_s'] = 1.0
        self.rewards['15_e'] = 1.0

        #上下左右
        self.t = dict();
        self.t['1_s'] = 5
        self.t['1_e'] = 2
        #
        self.t['2_s'] = 6
        self.t['2_w'] = 1
        self.t['2_e'] = 3
        #
        self.t['3_s'] = 7
        self.t['3_w'] = 2
        self.t['3_e'] = 4
        #
        self.t['4_s'] = 8
        self.t['4_w'] = 3
        #
        self.t['5_n'] = 1
        self.t['5_s'] = 9
        self.t['5_e'] = 6
        #
        self.t['6_n'] = 2
        self.t['6_s'] = 10
        self.t['6_w'] = 5
        self.t['6_e'] = 7
        #
        self.t['7_n'] = 3
        self.t['7_s'] = 11
        self.t['7_w'] = 6
        self.t['7_e'] = 8
        #
        self.t['8_n'] = 4
        self.t['8_s'] = 12
        self.t['8_w'] = 7
        #
        self.t['9_n'] = 5
        self.t['9_s'] = 13
        self.t['9_e'] = 10
        #
        self.t['10_n'] = 6
        self.t['10_s'] = 14
        self.t['10_w'] = 9
        self.t['10_e'] = 11
        #
        self.t['11_n'] = 7
        self.t['11_s'] = 15
        self.t['11_w'] = 10
        self.t['11_e'] = 12
        #
        self.t['12_n'] = 8
        self.t['12_s'] = 16
        self.t['12_w'] = 11
        #
        self.t['13_n'] = 9
        self.t['13_e'] = 14
        #
        self.t['14_n'] = 10
        self.t['14_w'] = 13
        self.t['14_e'] = 15
        #
        self.t['15_n'] = 11
        self.t['15_w'] = 14
        self.t['15_e'] = 16
        #
        self.t['16_n'] = 12
        self.t['16_w'] = 15


        self.gamma = 0.8

    def getTerminal(self):
        return self.terminal_states;

    def getGamma(self):
        return self.gamma;

    def getStates(self):
        return self.states

    def getActions(self):
        return self.actions

    def transform(self, state, action):  ##return is_terminal,state, reward
        if state in self.terminal_states:
            return True, state, 0

        key = '%d_%s' % (state, action);
        if key in self.t:
            next_state = self.t[key];
        else:
            next_state = state

        is_terminal = False
        if next_state in self.terminal_states:
            is_terminal = True

        if key not in self.rewards:
            r = 0.0
        else:
            r = self.rewards[key];

        return is_terminal, next_state, r;

    def gen_randompi_sample(self, num):
        state_sample = [];
        action_sample = [];
        reward_sample = [];
        for i in range(num):
            s_tmp = []
            a_tmp = []
            r_tmp = []

            s = self.states[int(random.random() * len(self.states))]
            t = False
            while False == t:
                a = self.actions[int(random.random() * len(self.actions))]
                t, s1, r = self.transform(s, a)
                s_tmp.append(s)
                r_tmp.append(r)
                a_tmp.append(a)
                s = s1
            state_sample.append(s_tmp)
            reward_sample.append(r_tmp)
            action_sample.append(a_tmp)
        return state_sample, action_sample, reward_sample