from sys import argv


file = argv[1]
print(file)
ts = 0
tj = 0
count = 0
with open(file, 'r') as f:
    cont = f.readlines()
    for inp in cont:
        content = inp.strip().split(" ")
        ts += float(content[1])
        tj += float(content[0])
    count = len(cont)
ts_a = ((ts/count) / 1000000) % 1000000
tj_a = ((tj/count) / 1000000) % 1000000
print("ts_average: {0} ms, tj_average: {1} ms".format(ts_a, tj_a))