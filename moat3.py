#!/usr/bin/env python
import sys
import unittest

# incr_dict
# params:
#   d - dict
#   t - tuple of nested data to insert
def incr_dict(d, t):
    curr_dict = d
    tup_size = len(t)
    for num,item in enumerate(t):
        #print 'num:',num,' item:',item
        if curr_dict is None:
            #print 'curr dict none'
            curr_dict = {}
        if num + 1 < tup_size: # if we are not at a leaf
            #print 'intermediate in tuple'
            # if it's none or 1, make it a new dict instead
            if item not in curr_dict:
                #print 'item -> none, making dict'
                curr_dict[item] = {}
            elif isinstance(curr_dict[item], (int, long)):
                #print 'item -> num, making dict'
                curr_dict[item] = {}
            # recursively assign dict as we move deeper
            #print 'assigning next dict, key:',item
            curr_dict = curr_dict[item]
        else: # if leaf, make 1
            #print 'leaf in tuple'
            if item in curr_dict and isinstance(curr_dict[item], dict):
                print >> sys.stderr, 'leaf in tuple, but not leaf in dict structure. not incrementing. tuple:', t
            else:
                if item not in curr_dict:
                    #print 'item -> none, making 0'
                    curr_dict[item] = 0
                #print 'incrementing'
                curr_dict[item] +=  1

class TestIncrDict(unittest.TestCase):

    def setUp(self):
        self.__dct = {}

    def test_incr_dict_abc(self):
        incr_dict(self.__dct, ('a','b','c'))
        self.assertTrue('a' in self.__dct)
        self.assertTrue(isinstance(self.__dct['a'], dict))
        self.assertTrue('b' in self.__dct['a'])
        self.assertTrue(isinstance(self.__dct['a']['b'], dict))
        self.assertTrue('c' in self.__dct['a']['b'])
        self.assertEqual(self.__dct['a']['b']['c'],1)

    def test_incr_dict_abc_again(self):
        incr_dict(self.__dct, ('a','b','c'))
        incr_dict(self.__dct, ('a','b','c'))
        self.assertTrue('a' in self.__dct)
        self.assertTrue(isinstance(self.__dct['a'], dict))
        self.assertTrue('b' in self.__dct['a'])
        self.assertTrue(isinstance(self.__dct['a']['b'], dict))
        self.assertTrue('c' in self.__dct['a']['b'])
        self.assertEqual(self.__dct['a']['b']['c'],2)

    def test_incr_dict_abf(self):
        incr_dict(self.__dct, ('a','b','f'))
        self.assertTrue('a' in self.__dct)
        self.assertTrue(isinstance(self.__dct['a'], dict))
        self.assertTrue('b' in self.__dct['a'])
        self.assertTrue(isinstance(self.__dct['a']['b'], dict))
        self.assertTrue('f' in self.__dct['a']['b'])
        self.assertEqual(self.__dct['a']['b']['f'],1)

    def test_incr_dict_arf(self):
        incr_dict(self.__dct, ('a','r','f'))
        self.assertTrue('a' in self.__dct)
        self.assertTrue(isinstance(self.__dct['a'], dict))
        self.assertTrue('r' in self.__dct['a'])
        self.assertTrue(isinstance(self.__dct['a']['r'], dict))
        self.assertTrue('f' in self.__dct['a']['r'])
        self.assertEqual(self.__dct['a']['r']['f'],1)

    def test_incr_dict_az(self):
        incr_dict(self.__dct, ('a','z'))
        self.assertTrue('a' in self.__dct)
        self.assertTrue(isinstance(self.__dct['a'], dict))
        self.assertTrue('z' in self.__dct['a'])
        self.assertEqual(self.__dct['a']['z'],1)

    def test_incr_all(self):
        self.__dct.clear()
        incr_dict(self.__dct, ('a','b','c'))
        incr_dict(self.__dct, ('a','b','c'))
        incr_dict(self.__dct, ('a','b','f'))
        incr_dict(self.__dct, ('a','r','f'))
        incr_dict(self.__dct, ('a','z'))
        # a b c
        self.assertTrue('a' in self.__dct)
        self.assertTrue(isinstance(self.__dct['a'], dict))
        self.assertTrue('b' in self.__dct['a'])
        self.assertTrue(isinstance(self.__dct['a']['b'], dict))
        self.assertTrue('c' in self.__dct['a']['b'])
        self.assertEqual(self.__dct['a']['b']['c'],2)
        # a b f
        self.assertTrue('f' in self.__dct['a']['b'])
        self.assertEqual(self.__dct['a']['b']['f'],1)
        # a r f
        self.assertTrue(isinstance(self.__dct['a']['r'], dict))
        self.assertTrue('f' in self.__dct['a']['r'])
        self.assertEqual(self.__dct['a']['r']['f'],1)
        # a z
        self.assertTrue('z' in self.__dct['a'])
        self.assertEqual(self.__dct['a']['z'],1)


if __name__ == '__main__':
    unittest.main()
