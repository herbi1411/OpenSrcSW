class Stack:
	def __init__(self):
		self.l = []
	def add(self,a):
		self.l.append(a)
	def pop(self):
		if self.isEmpty() == True:
			print("Stack is Empty")
		else:
			return self.l.pop()
	def top(self):
		if self.isEmpty() == True:
			print("Stack is Empty")
		else:
			return self.l[-1]
	def isEmpty(self):
		return len(self.l) == 0


