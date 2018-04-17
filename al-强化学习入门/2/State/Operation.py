# 设置名为Operation的类存放信息
class Operation(object):
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

#-------------------------------------
# 更新收益值矩阵
def updateReward(i, j):
    tempMatrix[i][j] = max((-1 + rewardMatrix[stateMatirx[i][j].upState[0]][stateMatirx[i][j].upState[1]]),

                           (-1 + rewardMatrix[stateMatirx[i][j].downState[0]][stateMatirx[i][j].downState[1]]),

                           (-1 + rewardMatrix[stateMatirx[i][j].leftState[0]][stateMatirx[i][j].leftState[1]]),

                           (-1 + rewardMatrix[stateMatirx[i][j].rightState[0]][stateMatirx[i][j].rightState[1]]))

#-------------------------------------
#状态
stateMatirx = [[] for i in range(4)]
for i in range(4):
    for j in range(4):
        stateMatirx[i].append(Operation(i, j))# 加状态

#-------------------------------------
# 收益值矩阵，存储结果
rewardMatrix = [
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

#-------------------------------------

# Value iteration

thresh = 0  #初始值，标准
delta = 0

for i in range(4):
    for j in range(4):
        if ((i == 0) and (j == 0)):# 障碍
            continue
        if i == 1 and j == 3:#
            continue
        if i == 2 and j == 1:#
            continue
        else:
            v = rewardMatrix[i][j]
            updateReward(i, j)
            delta = max(delta, abs(v - tempMatrix[i][j]))

rewardMatrix = tempMatrix #赋值

while delta > thresh:
    delta = 0
    for i in range(4):
        for j in range(4):
            if i == 0 and j == 0:#
                continue
            if i == 1 and j == 3:#(1,3)
                continue
            if i == 2 and j == 1:#(2,1)
                continue
            else:
                v = rewardMatrix[i][j]
                updateReward(i, j)
                delta = max(delta, abs(v - tempMatrix[i][j]))

    rewardMatrix = tempMatrix

#-------------------------------------
#结果
for i in range(0, 4):
    for j in range(0, 4):
        print(rewardMatrix[i][j])
    print("\n")