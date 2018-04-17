class State(object):
    def __init__(self, i, j):# 状态,Y,X

        # 向每个方向移动的概率
        self.up = 0.25
        self.down = 0.25
        self.left = 0.25
        self.right = 0.25

        # 矩阵 0 ～ 3
        if i > 0:
            self.upState = (i - 1, j)# 向上i-1
        else:
            self.upState = (i, j)# 上边界
        if i < 3:
            self.downState = (i + 1, j)# 向下i+1
        else:
            self.downState = (i, j)# 下边界
        if j > 0:
            self.leftState = (i, j - 1)# 向左j-1
        else:
            self.leftState = (i, j)# 左边界
        if j < 3:
            self.rightState = (i, j + 1)# 向右j+1
        else:
            self.rightState = (i, j)# 右边界

        # 障碍(1, 3)、(2, 1)
        # 等价于加了边界，见图
        if i == 0 and j == 3:
            self.downState = (i, j)
        if i == 1 and j == 1:
            self.downState = (i, j)
        if i == 1 and j == 2:
            self.rightState = (i, j)
        if i == 2 and j == 0:
            self.rightState = (i, j)
        if i == 2 and j == 2:
            self.leftState = (i, j)
        if i == 2 and j == 3:
            self.upState = (i, j)
        if i == 3 and j == 1:
            self.upState = (i, j)

    #-------------------------------------
    def updatePolicy(self, rewardMatrix):# 更新Policy

        oldUp = self.up
        oldDown = self.down
        oldLeft = self.left
        oldRight = self.right

        upValue = -1 + rewardMatirx[self.upState[0]][self.upState[1]]
        downValue = -1 + rewardMatirx[self.downState[0]][self.downState[1]]
        leftValue = -1 + rewardMatirx[self.leftState[0]][self.leftState[1]]
        rightValue = -1 + rewardMatirx[self.rightState[0]][self.rightState[1]]

        if (upValue >= downValue) and (upValue >= leftValue) and (upValue >= rightValue):
            self.up = 1
            self.down = 0
            self.left = 0
            self.right = 0
        elif (downValue >= upValue) and (downValue >= leftValue) and (downValue >= rightValue):
            self.up = 0
            self.down = 1
            self.left = 0
            self.right = 0
        elif (leftValue >= upValue) and (leftValue >= downValue) and (leftValue >= rightValue):
            self.up = 0
            self.down = 0
            self.left = 1
            self.right = 0
        else:
            self.up = 0
            self.down = 0
            self.left = 0
            self.right = 1
        if (oldUp == self.up) and (oldDown == self.down) and (oldLeft == self.left) and (oldRight == self.right):
            return True
        else:
            return False

#-------------------------------------
# 更新收益值矩阵
def updateReward(i, j):#下一步
    tempMatrix[i][j] = -1 + stateMatirx[i][j].up * rewardMatirx[stateMatirx[i][j].upState[0]][
        stateMatirx[i][j].upState[1]] \
                       + stateMatirx[i][j].down * rewardMatirx[stateMatirx[i][j].downState[0]][
        stateMatirx[i][j].downState[1]] \
                       + stateMatirx[i][j].left * rewardMatirx[stateMatirx[i][j].leftState[0]][
        stateMatirx[i][j].leftState[1]] \
                       + stateMatirx[i][j].right * rewardMatirx[stateMatirx[i][j].rightState[0]][
        stateMatirx[i][j].rightState[1]]

#-------------------------------------
#状态
stateMatirx = [[] for i in range(4)]
for i in range(4):
    for j in range(4):
        stateMatirx[i].append(State(i, j))# 加状态

#-------------------------------------
# 收益值矩阵，存储结果
rewardMatirx = [
    [0, 0, 0, 0],
    [0, 0, 0, 0],
    [0, 0, 0, 0],
    [0, 0, 0, 0]
]
# 备用收益值矩阵，先更新备用矩阵，再把值赋给收益值矩阵
tempMatrix = [
    [0, 0, 0, 0],
    [0, 0, 0, 0],
    [0, 0, 0, 0],
    [0, 0, 0, 0]
]

# 先给出一个policy
# 然后对该policy进行evaluation
# 再进行policy improvement
# 然后迭代上面的过程
#-------------------------------------
thresh = 0  #初始值，标准

#Policy Iteration
stableFlag = True
while stableFlag:
    # policy evaluation
    delta = 0
    for i in range(0, 4):
        for j in range(0, 4):
            if i == 0 and j == 0:# 障碍
                continue
            if i == 1 and j == 3:#
                continue
            if i == 2 and j == 1:#
                continue
            else:
                v = tempMatrix[i][j]
                updateReward(i, j)
                delta = max(delta, abs(tempMatrix[i][j] - v))#更新之后 变化大小
    rewardMatirx = tempMatrix# 赋值
    while delta > thresh:
        delta = 0
        for i in range(0, 4):
            for j in range(0, 4):
                if i == 0 and j == 0:#
                    continue
                if i == 1 and j == 3:#
                    continue
                if i == 2 and j == 1:#
                    continue
                else:
                    v = tempMatrix[i][j]
                    updateReward(i, j)
                    delta = max(delta, abs(tempMatrix[i][j] - v))
        rewardMatirx = tempMatrix
    # policy improvement
    for i in range(0, 4):
        for j in range(0, 4):
            if i == 0 and j == 0:#
                continue
            if i == 1 and j == 3:#
                continue
            if i == 2 and j == 1:#
                continue
            else:
                stableFlag = (stableFlag and stateMatirx[i][j].updatePolicy(rewardMatirx))

    stableFlag = not stableFlag
#-------------------------------------
#结果
for i in range(0, 4):
    for j in range(0, 4):
        print(rewardMatirx[i][j])
    print("\n")